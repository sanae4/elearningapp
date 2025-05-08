package com.example.elearning.service;

import com.example.elearning.dto.QuizDTO;
import com.example.elearning.model.Quiz;

import java.util.List;

public interface QuizService {

    /**
     * Récupère tous les quiz
     * @return Liste de tous les quiz
     */
    List<QuizDTO> getAllQuizzes();

    /**
     * Récupère un quiz par son ID
     * @param id ID du quiz
     * @return Quiz trouvé ou null
     */
    QuizDTO getQuizById(Long id);

    /**
     * Récupère tous les quiz d'un cours
     * @param courseId ID du cours
     * @return Liste des quiz du cours
     */
    List<QuizDTO> getQuizzesByCourseId(Long courseId);

    /**
     * Crée un nouveau quiz
     * @param quizDTO DTO du quiz à créer
     * @return Quiz créé
     */
    QuizDTO createQuiz(QuizDTO quizDTO);

    /**
     * Met à jour un quiz existant
     * @param id ID du quiz à mettre à jour
     * @param quizDTO DTO avec les nouvelles données
     * @return Quiz mis à jour
     */
    QuizDTO updateQuiz(Long id, QuizDTO quizDTO);

    /**
     * Supprime un quiz (marquage comme supprimé)
     * @param id ID du quiz à supprimer
     * @return true si supprimé avec succès
     */
    boolean deleteQuiz(Long id);

    /**
     * Supprime définitivement un quiz de la base de données
     * @param id ID du quiz à supprimer
     */
    void hardDeleteQuiz(Long id);

    /**
     * Génère automatiquement un quiz pour un cours
     * @param courseId ID du cours
     * @return Quiz généré
     */
    QuizDTO generateQuizForCourse(Long courseId);

    /**
     * Corrige un quiz en fonction des réponses fournies
     * @param quizId ID du quiz
     * @param reponses Liste des réponses aux questions
     * @return Résultat du quiz
     */
    QuizDTO correctQuiz(Long quizId, List<String> reponses);

    QuizDTO getQuizByLeconId(Long leconId);
}
