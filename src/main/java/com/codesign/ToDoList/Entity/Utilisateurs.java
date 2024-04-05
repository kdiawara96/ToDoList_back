package com.codesign.ToDoList.Entity;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utilisateurs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE utilisateurs SET deleted = true WHERE id=?")
public class Utilisateurs {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = true, length = 50, unique = false)
    private String nom;

    @Column(name = "prenom", nullable = true, length = 50, unique = false)
    private String prenom;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email", nullable = true, length = 50, unique = true)
    private String email;

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateCreation", nullable = true, length = 50, unique = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateModifier", nullable = true, length = 50, unique = false)
    private LocalDateTime dateModifier ;

    @Column(name = "deleted")
    private Boolean deleted = false;

    @Column(name = "statut")
    private Boolean statut = true;
}
