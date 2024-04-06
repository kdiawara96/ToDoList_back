package com.codesign.ToDoList.Services;

import com.codesign.ToDoList.Entity.Utilisateurs;

public interface UtilisateursServices {
    
    Utilisateurs save(Utilisateurs utilisateurs);
    Utilisateurs update(Utilisateurs utilisateurs);
    Utilisateurs findByEmail(String email);

}
