package com.codesign.ToDoList.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codesign.ToDoList.Entity.ToDo;

public interface ToDoRepo extends JpaRepository<ToDo, Long>{
    
    ToDo findByDesignation(String designation);
}
