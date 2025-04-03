package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Enseignant extends User {

    private String biographie;
    private boolean status;
    private String specialite;
    private String anneesExperience;

    @OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Course> courses;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enseignant enseignant = (Enseignant) o;
        return Objects.equals(getId(), enseignant.getId()); // Utiliser getId() car id est dans la classe parente User
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}