package com.codesign.ToDoList.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesign.ToDoList.Entity.ToDo;
import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Enums.Etats;

public interface ToDoRepo extends JpaRepository<ToDo, Long>{
    
    ToDo findByDesignation(String designation);
    List<ToDo> findAllByEtatAndUtilisateurs(Etats etat, Utilisateurs utilisateurs);
}
