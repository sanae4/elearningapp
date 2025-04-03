package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Etudiant extends User {

    private String certificat;
    private String langue;
    @ManyToMany
    @JoinTable(
            name = "etudiant_course",
            joinColumns = @JoinColumn(name = "etudiant_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonBackReference
    private Set<Course> courses;
}