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

    private String titre;
    private String type; // Vidéo, Texte, etc.
    @Lob
    private byte[] contenu;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String resumer;
    // Relation Many-to-One avec Leçon
    @ManyToOne
    @JoinColumn(name = "lecon_id", nullable = false)
    private Leçon lecon;
}