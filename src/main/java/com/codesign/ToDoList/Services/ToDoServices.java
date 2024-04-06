package com.codesign.ToDoList.Services;

import java.util.List;

import com.codesign.ToDoList.Entity.ToDo;
import com.codesign.ToDoList.Enums.Etats;

public interface ToDoServices {
    
    ToDo create(ToDo data, Long idUser);
    ToDo update(ToDo data, Long idUser);
    ToDo changerEtat(Long idTodo, Long iduser, Etats etatDuToDo);
    String deleteToDpo(Long idTodo, Long iduser);
    List<ToDo> findAllByEtatAndUtilisateurs(Long iduser, Etats etat);
}
