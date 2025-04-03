package com.example.elearning.service;

import com.example.elearning.dto.LeçonCreateUpdateDTO;
import com.example.elearning.dto.LeçonDTO;

import java.util.List;

public interface LeçonService {

    /**
     * Récupère toutes les leçons
     * @return Liste de toutes les leçons
     */
    List<LeçonDTO> getAllLeçons();

    /**
     * Récupère une leçon par son ID
     * @param id ID de la leçon à récupérer
     * @return La leçon correspondante
     */
    LeçonDTO getLeçonById(Long id);

    /**
     * Crée une nouvelle leçon
     * @param leçonDTO DTO contenant les informations de la leçon à créer
     * @return La leçon créée
     */
    LeçonDTO createLeçon(LeçonCreateUpdateDTO leçonDTO);

    /**
     * Met à jour une leçon existante
     * @param id ID de la leçon à mettre à jour
     * @param leçonDTO DTO contenant les nouvelles informations
     * @return La leçon mise à jour
     */
    LeçonDTO updateLeçon(Long id, LeçonCreateUpdateDTO leçonDTO);

    /**
     * Supprime une leçon (suppression physique)
     * @param id ID de la leçon à supprimer
     */
    void deleteLeçon(Long id);

    /**
     * Marque une leçon comme supprimée (suppression logique)
     * @param id ID de la leçon à marquer comme supprimée
     * @return La leçon mise à jour
     */
    LeçonDTO supprimerLeçon(Long id);



    List<LeçonDTO> getLeçonsByCourse(Long courseId);

    /**
     * Recherche des leçons par titre
     * @param titre Titre à rechercher
     * @return Liste des leçons correspondantes
     */
    List<LeçonDTO> searchLeçonsByTitle(String titre);
}
