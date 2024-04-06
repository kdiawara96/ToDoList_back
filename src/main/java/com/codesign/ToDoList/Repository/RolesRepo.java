package com.codesign.ToDoList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesign.ToDoList.Entity.Roles;

public interface RolesRepo extends JpaRepository<Roles, Long> {

    Roles findByRole(String role);
}
