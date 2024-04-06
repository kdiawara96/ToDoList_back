package com.codesign.ToDoList.ServicesImpl;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Repository.UtilisateursRepo;
import com.codesign.ToDoList.Services.UtilisateursServices;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilisateursImpl implements UtilisateursServices{

    private final UtilisateursRepo repo;

    @Override
    public Utilisateurs save(Utilisateurs utilisateurs) {
        
        String email = utilisateurs.getEmail();

        if(email != null){
            Utilisateurs add = repo.findByEmail(email);
            if (add == null || add.getId() == utilisateurs.getId() ) {
                utilisateurs.setEmail(email);
            }else{
                throw new RuntimeException("Il y a un utilisateur de même Email!");
            }
        }
        
        return repo.save(utilisateurs);
    }

    @Override
    public Utilisateurs update(Utilisateurs utilisateurs) {

     Utilisateurs add = repo.findById(utilisateurs.getId()).orElseThrow(() -> new NoSuchElementException("Utilisateurs n'existe pas!"));

     add.setDateModifier(LocalDateTime.now());
     add.setNom(utilisateurs.getNom());
     add.setPrenom(utilisateurs.getPrenom());
     add.setUsername(utilisateurs.getUsername());

     String email = utilisateurs.getEmail();

     if(email != null){
         Utilisateurs user = repo.findByEmail(email);
         if (user == null || user.getId() == utilisateurs.getId() ) {
            add.setEmail(email);
         }else{
             throw new RuntimeException("Il y a un utilisateur de même Email!");
         }
     }

     return repo.save(add);

    }

    @Override
    public Utilisateurs findByEmail(String email) {
       return repo.findByEmail(email);
    }

    
    
}
