package com.example.elearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rapports")
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;

    @Column(name = "est_archive")
    private int estArchive;

    @Temporal(TemporalType.DATE)
    private Date date;

    // Relation Many-to-One avec Enseignant
    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    // Relation Many-to-Many avec Etudiant
    @ManyToMany
    @JoinTable(
            name = "rapport_etudiant", // Correction du nom de la table
            joinColumns = @JoinColumn(name = "rapport_id"),
            inverseJoinColumns = @JoinColumn(name = "etudiant_id")
    )
    private List<Etudiant> etudiants; // Changement de "etudiant" à "etudiants" pour plus de clarté

    // Méthodes
    public void creerManuel() {
        // Logique pour créer manuellement un rapport
    }

    public void creerRapport() {
        // Logique pour créer un rapport
    }

    public void getRapport() {
        // Logique pour récupérer un rapport
    }

    public void archiverRapport() {
        this.estArchive = 1;
        // Logique supplémentaire pour archiver un rapport
    }
}
