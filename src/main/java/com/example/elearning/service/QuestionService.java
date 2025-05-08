package com.example.elearning.service;

import com.example.elearning.dto.QuestionDTO;

import java.util.List;

public interface QuestionService {

    /**
     * Récupère toutes les questions
     * @return Liste de toutes les questions
     */
    List<QuestionDTO> getAllQuestions();

    /**
     * Récupère une question par son ID
     * @param id ID de la question
     * @return Question trouvée ou null
     */
    QuestionDTO getQuestionById(Long id);

    /**
     * Récupère toutes les questions d'un quiz
     * @param quizId ID du quiz
     * @return Liste des questions du quiz
     */
    List<QuestionDTO> getQuestionsByQuizId(Long quizId);

    /**
     * Récupère toutes les questions d'un type spécifique
     * @param type Type de question
     * @return Liste des questions du type spécifié
     */
    List<QuestionDTO> getQuestionsByType(String type);

    /**
     * Crée une nouvelle question
     * @param questionDTO DTO de la question à créer
     * @return Question créée
     */
    QuestionDTO createQuestion(QuestionDTO questionDTO);

    /**
     * Met à jour une question existante
     * @param id ID de la question à mettre à jour
     * @param questionDTO DTO avec les nouvelles données
     * @return Question mise à jour
     */
    QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO);

    /**
     * Supprime une question
     * @param id ID de la question à supprimer
     */
    void deleteQuestion(Long id);

    /**
     * Vérifie si une réponse est correcte pour une question
     * @param questionId ID de la question
     * @param reponse Réponse à vérifier
     * @return true si la réponse est correcte
     */
    boolean verifierReponse(Long questionId, String reponse);
}
