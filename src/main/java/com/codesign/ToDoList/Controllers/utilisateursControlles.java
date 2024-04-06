package com.codesign.ToDoList.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Others.Message;
import com.codesign.ToDoList.Services.UtilisateursServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor

public class utilisateursControlles {
    
    private final UtilisateursServices service;

    // resourceNames.put("utilisateur/save", "kfpvkfdpofkdkpofkpfdkpokàofkpdoikofdkfdpfdkfkfdd");
    @PostMapping("/utilisateur/save")
    public ResponseEntity<Object> save(@RequestBody Utilisateurs data) {
        try {
              if (data == null) {
            throw new MissingRequestValueException("La data est obligatoire !");
          }
            return Message.Response("Création de user effectué !", HttpStatus.OK, service.save(data));
        } catch (Exception e) {
           return Message.Response("Erreur de création de user !", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    // resourceNames.put("utilisateur/update", "dldsdsnodsnjsoijdsosjinodssjsiojsosjdsdodsjoidsss");
    @PutMapping("/utilisateur/update")
    public ResponseEntity<Object> update(@RequestBody Utilisateurs data) {
        try {
              if (data == null) {
            throw new MissingRequestValueException("La data est obligatoire !");
          }
            return Message.Response("Création de user effectué !", HttpStatus.OK, service.update(data));
        } catch (Exception e) {
           return Message.Response("Erreur de création de user !", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
