package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultatDTO {
    private Long id;
    private int score;
    private boolean estpasse;
    private Long quizId;
    private String quizTitre;
}
