package com.codesign.ToDoList.models;

import java.util.Set;

import com.codesign.ToDoList.Entity.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UsersModel {
    
    Long id;
    String nom;
    String email;
    String photo;
    String username;
    Set<Roles> roles;
    String accessToken;
    String refreshToken;

}