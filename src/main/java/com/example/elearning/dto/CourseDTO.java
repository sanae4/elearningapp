package com.example.elearning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String about;
    private String courselevel;
    private String titreCours;
    private String description;
    private double prix;
    private Date datePublication;
    private String image;
    private String langage;
    private String statusCours;
    @JsonProperty("lecons")
    private List<LeçonDTO> lecons;
    // Références aux entités liées
    private Long enseignantId;
    private String enseignantNom; // Pour afficher le nom de l'enseignant

    // Ajout de la référence à la catégorie
    private Long categoryId;
    // Ajouter un objet EnseignantDTO complet au lieu d'un simple ID
    private EnseignantDTO enseignant;

    // Ajouter un objet CategoryDTO complet au lieu d'un simple ID
    private CategoryDTO category;

    // IDs des étudiants inscrits
    private Set<Long> etudiantIds;
}
