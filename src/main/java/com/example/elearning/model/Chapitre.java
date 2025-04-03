package com.example.elearning.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
public class Chapitre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resumer;
    private String titre;
    private String type; // Vidéo, Texte, etc.
    private String contenu;

    // Relation Many-to-One avec Leçon
    @ManyToOne
    @JoinColumn(name = "leçon_id", nullable = false)
    private Leçon leçon;
}