package com.codesign.ToDoList.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesign.ToDoList.Entity.ToDo;
import com.codesign.ToDoList.Entity.Utilisateurs;
import com.codesign.ToDoList.Enums.Etats;
import com.codesign.ToDoList.Others.Message;
import com.codesign.ToDoList.Services.ToDoServices;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor

public class TodoControllers {

    private final ToDoServices service;

    
    // resourceNames.put("todolist/save", "kjkkdidhduhdidhishsdjjddkjjdjjdjjdjjsjjjjjjjjdsjjddjjsjjdsjjjisidsissoisssss");
    @PostMapping("/todolist/save/{idUser}")
    public ResponseEntity<Object> save(@RequestBody ToDo data, @PathVariable Long idUser) {
        try {
              if (data == null) {
            throw new MissingRequestValueException("La data est obligatoire !");
          }
            return Message.Response("Création du todo effectué !", HttpStatus.OK, service.create(data, idUser));
        } catch (Exception e) {
           return Message.Response("Erreur de création du todo !", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

      // resourceNames.put("todolist/update", "dksidjoidjdoijdodijdojdqojqidushduisudiuissisuusuusiuisiusssis");
      @PutMapping("/todolist/update/{idUser}")
      public ResponseEntity<Object> update(@RequestBody ToDo data, @PathVariable Long idUser) {
          try {
                if (data == null) {
              throw new MissingRequestValueException("La data est obligatoire !");
            }
              return Message.Response("Mise à jour du todo effectué !", HttpStatus.OK, service.update(data, idUser));
          } catch (Exception e) {
            return Message.Response("Erreur de mise à jour du todo !", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }

      // resourceNames.put("todolist/changerEtat_AFAIRE", "kdjdiojifdojddjudiisiddsdsddsfiofdjifdosjfojfiofsjofjfdofdjffiofdf");
      @PutMapping("/todolist/changerEtat_AFAIRE/{idTodo}/{idUser}")
      public ResponseEntity<Object> changerEtat_AFAIRE(@PathVariable Long idTodo, @PathVariable Long idUser) {
          try {
              return Message.Response("Changement du todo effectué !", HttpStatus.OK, service.changerEtat(idTodo, idUser, Etats.A_FAIRE));
          } catch (Exception e) {
            return Message.Response("Erreur changement du todo!", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }

        // resourceNames.put("todolist/changerEtatEncours", "oijdodjoijdoisjdijsdsidssdojidsojsdoisjidjdodsjsosdsjsiojsijsij");
        @PutMapping("/todolist/changerEtatEncours/{idTodo}/{idUser}")
        public ResponseEntity<Object> changerEtatEncours(@PathVariable Long idTodo, @PathVariable Long idUser) {
            try {
                return Message.Response("Changement du todo effectué !", HttpStatus.OK, service.changerEtat(idTodo, idUser, Etats.EN_COURS));
            } catch (Exception e) {
              return Message.Response("Erreur changement du todo!", HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

      // resourceNames.put("todolist/changerEtatTerminer", "jdijdiojoidjoidjoisjdsisdoisdsjoisijddijsijdijoisdsds");
      @PutMapping("/todolist/changerEtatTerminer/{idTodo}/{idUser}")
      public ResponseEntity<Object> changerEtatTerminer(@PathVariable Long idTodo, @PathVariable Long idUser) {
          try {
              return Message.Response("Changement du todo effectué ! !", HttpStatus.OK, service.changerEtat(idTodo, idUser, Etats.TERMINE));
          } catch (Exception e) {
            return Message.Response("Erreur changement du todo!", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }
      // resourceNames.put("todolist/findAllByEtatAndUtilisateurs_A_Faire", "idjifjdfoijfdfdoijfdodfsjoqfjoifdsjffofdjfoifdifjjioifd");
      @PutMapping("/todolist/findAllByEtatAndUtilisateurs_A_Faire/{idUser}")
      public ResponseEntity<Object> findAllByEtatAndUtilisateurs_A_Faire(@PathVariable Long idUser) {
          try {
              return Message.Response("Recuperation du todo effectué ! !", HttpStatus.OK, service.findAllByEtatAndUtilisateurs(idUser, Etats.A_FAIRE));
          } catch (Exception e) {
            return Message.Response("Erreur de recuperation du todo !", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }
    
      // resourceNames.put("todolist/findAllByEtatAndUtilisateurs_EN_Cours", "iuiuokoiiiiiiiiiiioioioiioudiudiuiudddiudiuudiuiudididdi");
      @PutMapping("/todolist/findAllByEtatAndUtilisateurs_EN_Cours/{idUser}")
      public ResponseEntity<Object> findAllByEtatAndUtilisateurs_EN_Cours(@PathVariable Long idUser) {
          try {
              return Message.Response("Recuperation du todo effectué !", HttpStatus.OK, service.findAllByEtatAndUtilisateurs(idUser, Etats.EN_COURS));
          } catch (Exception e) {
            return Message.Response("Erreur de recuperation du todo !", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }

    
      // resourceNames.put("todolist/findAllByEtatAndUtilisateurs_TERMINER", "dijfoijfdiojfdosjiffjoifijfjfiifosjdoiiffjiioffijofiofodiofijfoifd");
      @PutMapping("/todolist/findAllByEtatAndUtilisateurs_TERMINER/{idUser}")
      public ResponseEntity<Object> findAllByEtatAndUtilisateurs_TERMINER(@PathVariable Long idUser) {
          try {
              return Message.Response("Recuperation du todo effectué !", HttpStatus.OK, service.findAllByEtatAndUtilisateurs(idUser, Etats.TERMINE));
          } catch (Exception e) {
            return Message.Response("Erreur de recuperation du todo !", HttpStatus.BAD_REQUEST, e.getMessage());
          }
      }

}
