package com.codesign.ToDoList.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE todo SET deleted = true WHERE id=?")

public class ToDo {
    
       @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comlpleted")
    private Boolean comlpleted = Boolean.FALSE;
    
    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateCreation", nullable = true, length = 50, unique = false)
    private LocalDateTime dateCreation = LocalDateTime.now();

    @JsonFormat(pattern = "dd-MM-yy HH:mm", shape = Shape.STRING)
    @Column(name = "dateModifier", nullable = true, length = 50, unique = false)
    private LocalDateTime dateModifier ;

    @Column(name = "deleted")
    private Boolean deleted = false;


    @ManyToOne
    private Utilisateurs utilisateurs;
}
