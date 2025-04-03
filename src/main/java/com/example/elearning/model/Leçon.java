package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Getter
public class Leçon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titreLeçon;
    private String description;

    private int nbrChapitres;
    private String conseilsEnseignant;
    private boolean estSupprimé;

    @Column(name = "use_AI") // Optionnel: précise le nom de colonne en BDD
    private boolean useAI; // Renommer en suivant les conventions Java

    // Getter correct suivant les conventions
    public boolean isUseAI() {
        return useAI;
    }

    public void setUseAI(boolean useAI) {
        this.useAI = useAI;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;



    public void supprimerLeçon() {
        this.estSupprimé = true;
    }



}