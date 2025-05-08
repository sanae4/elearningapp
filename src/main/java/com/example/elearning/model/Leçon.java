package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;

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
    // Dans la classe Lecon
    @OneToOne(mappedBy = "lecon", cascade = CascadeType.ALL)
    @JsonManagedReference("lecon-quiz")
    private Quiz quiz;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "lecon", cascade = CascadeType.ALL)
    @JsonManagedReference // This is the forward part of the reference
    private List<Chapitre> chapitres;

    public void supprimerLeçon() {
        this.estSupprimé = true;
    }



}