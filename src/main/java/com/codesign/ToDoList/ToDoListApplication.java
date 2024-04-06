package com.codesign.ToDoList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'run'");
	}

}
