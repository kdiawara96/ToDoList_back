package com.codesign.ToDoList.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id=?")

public class Roles {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", nullable = false, length = 20, unique = true)
    private String role;

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "date_creation", nullable = true, length = 50, unique = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

     @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "date_modification", nullable = true, length = 50, unique = false)
    private LocalDateTime dateModification ;

    @Column(name = "deleted")
    private Boolean deleted = false;


    //------------------------MAPPING------------------------
     @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_users", joinColumns = {
            @JoinColumn(name = "id_roles") }, inverseJoinColumns = {
            @JoinColumn(name = "id_utilisateurs") })
    List<Utilisateurs> utilisateurs = new ArrayList<>();

    
}
