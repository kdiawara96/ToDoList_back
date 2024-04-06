package com.codesign.ToDoList.ServicesImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.codesign.ToDoList.Entity.ToDo;
import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Enums.Etats;
import com.codesign.ToDoList.Repository.ToDoRepo;
import com.codesign.ToDoList.Repository.UtilisateursRepo;
import com.codesign.ToDoList.Services.ToDoServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoImpl implements ToDoServices{
    
    private final ToDoRepo repo;
    private final UtilisateursRepo uRepo;

    @Override
    public ToDo create(ToDo data, Long idUser) {
      Utilisateurs add = uRepo.findById(idUser).orElseThrow(() -> new NoSuchElementException("Utilisateurs n'existe pas!"));
      data.setUtilisateurs(add);
      return repo.save(data);
    }

    @Override
    public ToDo update(ToDo data, Long idUser) {

        Utilisateurs add = uRepo.findById(idUser).orElseThrow(() -> new NoSuchElementException("Utilisateurs n'existe pas!"));

        ToDo tod = repo.findById(data.getId()).orElseThrow(()-> new NoSuchElementException("ToDo n'existe pas !"));

        if (tod.getUtilisateurs() != add) {
            throw new IllegalArgumentException("Vous n'êtes pas apte pour modifier le toDo");
        }

        tod.setDateModifier(LocalDateTime.now());
        tod.setContent(data.getContent());
        tod.setDesignation(data.getDesignation());
        
        return repo.save(tod);
    }

    @Override
    public ToDo changerEtat(Long idTodo, Long iduser, Etats etatDuToDo) {

        ToDo tod = repo.findById(idTodo).orElseThrow(()-> new NoSuchElementException("ToDo n'existe pas !"));
        tod.setEtat(etatDuToDo);
        tod.setDateModifier(LocalDateTime.now());
        return repo.save(tod);

    }

    @Override
    public String deleteToDpo(Long idTodo, Long iduser) {
        ToDo tod = repo.findById(idTodo).orElseThrow(()-> new NoSuchElementException("ToDo n'existe pas !"));
        Utilisateurs add = uRepo.findById(iduser).orElseThrow(() -> new NoSuchElementException("Utilisateurs n'existe pas!"));

        if (tod.getUtilisateurs() != add) {
            throw new IllegalArgumentException("Vous n'etes pas apte pour supprimer le toDo");
        }
        tod.setDateModifier(LocalDateTime.now());
        tod.setDeleted(true);
        return "ToDo supprime avec succes !";
    }

    @Override
    public List<ToDo> findAllByEtatAndUtilisateurs(Long iduser, Etats etat) {
      Utilisateurs add = uRepo.findById(iduser).orElseThrow(() -> new NoSuchElementException("Utilisateurs n'existe pas!"));
      return repo.findAllByEtatAndUtilisateurs(etat, add);
    }

    
}

