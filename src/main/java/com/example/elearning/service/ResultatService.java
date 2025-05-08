package com.example.elearning.service;

import com.example.elearning.dto.ResultatDTO;

import java.util.List;

public interface ResultatService {

    /**
     * Récupère tous les résultats
     * @return Liste de tous les résultats
     */
    List<ResultatDTO> getAllResultats();

    /**
     * Récupère un résultat par son ID
     * @param id ID du résultat
     * @return Résultat trouvé ou null
     */
    ResultatDTO getResultatById(Long id);

    /**
     * Récupère le résultat d'un quiz spécifique
     * @param quizId ID du quiz
     * @return Résultat du quiz
     */
    ResultatDTO getResultatByQuizId(Long quizId);

    /**
     * Récupère tous les résultats réussis
     * @return Liste des résultats réussis
     */
    List<ResultatDTO> getResultatsReussis();

    /**
     * Récupère tous les résultats échoués
     * @return Liste des résultats échoués
     */
    List<ResultatDTO> getResultatsEchoues();

    /**
     * Crée un nouveau résultat
     * @param resultatDTO DTO du résultat à créer
     * @return Résultat créé
     */
    ResultatDTO createResultat(ResultatDTO resultatDTO);

    /**
     * Met à jour un résultat existant
     * @param id ID du résultat à mettre à jour
     * @param resultatDTO DTO avec les nouvelles données
     * @return Résultat mis à jour
     */
    ResultatDTO updateResultat(Long id, ResultatDTO resultatDTO);

    /**
     * Supprime un résultat
     * @param id ID du résultat à supprimer
     */
    void deleteResultat(Long id);

    /**
     * Calcule si un quiz est réussi en fonction du score
     * @param resultatId ID du résultat
     * @param scoreMinimum Score minimum pour réussir
     * @return true si le quiz est réussi
     */
    boolean calculerReussite(Long resultatId, int scoreMinimum);

    /**
     * Génère un rapport de résultat
     * @param resultatId ID du résultat
     * @return Rapport de résultat
     */
    String genererRapport(Long resultatId);

    /**
     * Calcule le score moyen pour un quiz spécifique
     * @param quizId ID du quiz
     * @return Score moyen
     */
    Double calculerScoreMoyen(Long quizId);
}
