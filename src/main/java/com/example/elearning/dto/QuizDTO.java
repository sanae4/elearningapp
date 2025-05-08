package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Long id;
    private String titre;
    private boolean estsupprimer;
    private String quizType; // "COURSE" or "LESSON"

    // Course information (if quiz is for a course)
    private Long courseId;


    // Lesson information (if quiz is for a lesson)
    private Long leconId;


    private List<QuestionDTO> questions = new ArrayList<>();
    private Long resultatId;
    private ResultatDTO resultat;
}
