package com.codesign.ToDoList.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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

    @Column(name = "password", nullable = true, unique = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateCreation", nullable = true, length = 50, unique = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateModifier", nullable = true, length = 50, unique = false)
    private LocalDateTime dateModifier ;

    @Column(name = "deleted")
    private Boolean deleted = false;

    //---------------------MAPPING------------------------

      // ROLES
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "roles_users", joinColumns = {
            @JoinColumn(name = "id_utilisateurs") },
            inverseJoinColumns = {
            @JoinColumn(name = "id_roles") })
    private Set<Roles> roles ;

    @OneToMany(mappedBy = "utilisateurs")
    private List<ToDo> toDo;

}
