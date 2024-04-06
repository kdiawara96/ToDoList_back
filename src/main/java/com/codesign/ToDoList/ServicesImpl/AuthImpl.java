package com.codesign.ToDoList.ServicesImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Repository.UtilisateursRepo;
import com.codesign.ToDoList.Services.AuthServices;




@Service
// @RequiredArgsConstructor
public class AuthImpl implements AuthServices{
    
     @Autowired
    private  UtilisateursRepo repo;
    private  JwtEncoder jwtEncoder;
    private  JwtDecoder jwtDecoder;
    private  AuthenticationManager authenticationManager;
    private  UserDetailsService userDetailsService;


  public AuthImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UtilisateursRepo repo) {
       this.jwtEncoder = jwtEncoder;
       this.jwtDecoder = jwtDecoder;
       this.authenticationManager = authenticationManager;
       this.userDetailsService = userDetailsService;
    //    this.repo = repo;
    }

    
    @Override
    public ResponseEntity<Object> jwt(String grantType, String email, String password, boolean ouiRefresh, String refreshToken) {
      
        String subject = null;
        String scope = null;
        
        if (grantType.equals("password")){

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            subject= authentication.getName();
            scope = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors
                    .joining(" "));


        }else if(grantType.equals("google")){

            //NOUS ALLONS VERIFIER L'EXISTANCE DE L'UTILISATEUR AVEC LES INFOS DE GOOGLE
              Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            subject= authentication.getName();
            scope = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors
                    .joining(" "));

          } else if(grantType.equals("refreshToken")){
            if (refreshToken == null){
                //Un message si la durée du refresh token à expiré
                return new ResponseEntity<>(Map.of("errorMessage : ","Refresh Token is requeried"), HttpStatus.UNAUTHORIZED);
            }
          
            Jwt decodeJWT = null;
            try {
                //quand nous decodons il va verifier s'il n'est pas expirer
                decodeJWT = jwtDecoder.decode(refreshToken);
            } catch (JwtException e) {
                return new ResponseEntity<>(Map.of("errorMessage :",e.getMessage()), HttpStatus.UNAUTHORIZED);
            }
            //String subjet = decodeJWT.getSubject();
            subject = decodeJWT.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        }

        UsersModel authModel = new UsersModel();
        Utilisateurs currentUser = repo.findByEmail(email);
        // List<String> roles ;
        if(currentUser != null){
            authModel.setId(currentUser.getId());
            authModel.setNom(currentUser.getNom());
            authModel.setRoles(currentUser.getRoles());
            authModel.setUsername(currentUser.getUsername());
            authModel.setEmail(currentUser.getEmail());
        }
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(ouiRefresh?10:10, ChronoUnit.DAYS))
                .issuer("todo")
                .claim("scope", scope)
                .build();
        String jwtAccesToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

        authModel.setAccessToken(jwtAccesToken);
        if(ouiRefresh){
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(10, ChronoUnit.DAYS))
                    .issuer("todo")
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
        
            authModel.setRefreshToken(jwtRefreshToken);
        }

        return  new ResponseEntity<>(authModel, HttpStatus.OK);
    }
    
}
