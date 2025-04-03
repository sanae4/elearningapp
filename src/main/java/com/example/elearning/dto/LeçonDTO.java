package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeçonDTO {
    private Long id;
    private String titreLeçon;
    private String description;

    private int nbrChapitres;
    private String conseilsEnseignant;
    private boolean estSupprimé;
    private boolean useAI;
    // Référence au cours associé
    private Long courseId;
    private String titreCours; // Pour afficher le titre du cours associé
}