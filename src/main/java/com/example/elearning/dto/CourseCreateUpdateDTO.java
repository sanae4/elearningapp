package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateUpdateDTO {
    private String about;
    private String courselevel;
    private String titreCours;
    private String description;
    private double prix;
    private Date datePublication;
    private String image;
    private String langage;
    private String statusCours;

    // Références aux entités liées
    private Long enseignantId;

    // Ajout de la référence à la catégorie
    private Long categoryId;
}
