package com.example.elearning.Response;

import com.example.elearning.dto.CourseDTO;
import lombok.Data;

@Data
public class LeçonResponseDTO {
    private Long id;
    private String titreLeçon;
    private String description;
    private String résumé;
    private int nbrChapitres;
    private String conseilsEnseignant;
    private CourseDTO course;
    private boolean useai;
}

