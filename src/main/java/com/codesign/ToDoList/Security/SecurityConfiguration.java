package com.codesign.ToDoList.Security;

import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Services.UtilisateursServices;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfiguration {

    private static final String[] PUBLIC_MATCHERS = {
     "/todo/auth",
     "/todo/test",
    //  "/todo/utilisateurs/otp/**",
    };

    
    //Nous allons injecter ces attributs et declarer dans le constructeur
    private final RsakeysConfig rsakeysConfig_;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UtilisateursServices utilisateursService;

    @Bean
    public AuthenticationManager authenticationManager(){
        var authProvider = new DaoAuthenticationProvider();
        //Nous allons attacher le passwordEncoder que nous volons utiliser
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Utilisateurs utilisateurs = utilisateursService.findByEmail(email);
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                utilisateurs.getRoles().forEach(role->{
                    authorities.add(new SimpleGrantedAuthority(role.getRole()));
                });
                return new User(utilisateurs.getUsername(), utilisateurs.getPassword(),authorities);
            }
        });

         return null;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws  Exception{

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .httpBasic(Customizer.withDefaults());
                // .oauth2Login(Customizer.withDefaults());

                return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    //Pour verifier la signature du token j'ai besoin du public key
    @Bean
    JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsakeysConfig_.publicKey()).build();
    }
    
    //Pour signer le token j'ai besoin d'utiliser le private key et public key
    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsakeysConfig_.publicKey()).privateKey(rsakeysConfig_.privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

}
