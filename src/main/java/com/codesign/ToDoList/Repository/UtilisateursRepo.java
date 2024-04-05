package com.codesign.ToDoList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesign.ToDoList.Entity.Utilisateurs;

public interface UtilisateursRepo extends JpaRepository<Utilisateurs, Long> {

    Utilisateurs findByEmail(String email);
    Utilisateurs findByUsername(String username);
}
