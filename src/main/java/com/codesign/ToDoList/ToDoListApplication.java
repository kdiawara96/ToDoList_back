package com.codesign.ToDoList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codesign.ToDoList.Entity.Roles;
import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Repository.RolesRepo;
import com.codesign.ToDoList.Repository.UtilisateursRepo;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ToDoListApplication implements CommandLineRunner{

	private final UtilisateursRepo uRepo;
	private final RolesRepo rRepo;

	public static void main(String[] args) {
		SpringApplication.run(ToDoListApplication.class, args);
	}

		@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {

	    LocalDateTime now = LocalDateTime.now();
		PasswordEncoder passwordEncoder = passwordEncoder();

		String roleUserAdmin = "ADMIN";
		String roleUser = "UTILISATEUR";

		Roles adminRole = rRepo.findByRole(roleUserAdmin);
		Roles  userRole = rRepo.findByRole(roleUser);

		Set<Roles> roles = new HashSet<>();

		if (userRole == null) {
			Roles role = new Roles();
			role.setRole(roleUser);
			role.setDeleted(false);
			role.setDateCreation(now);

			rRepo.save(role);
		}

		if (adminRole == null) {
			Roles role = new Roles();
			role.setRole(roleUserAdmin);
			role.setDateCreation(now);
			roles.add(rRepo.save(role));
		}else{
			roles.add(adminRole);
		}


		String email = "todol@gmail.com";
		String username = "todo@2024";

		Utilisateurs user = new Utilisateurs();

		Utilisateurs usEmail = uRepo.findByEmail(email);
		Utilisateurs usUsername = uRepo.findByUsername(username);

		if (usEmail == null && usUsername == null) {
	   
			user.setNom("pay");
			user.setPrenom("mal");
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(passwordEncoder.encode("todo@2024"));
			user.setRoles(roles);
			user.setDateCreation(now);
			user.setDeleted(false);
	
			   uRepo.save(user);
	
			}
	}


}
