package com.example.elearning.service;

import com.example.elearning.model.Chapitre;

import java.util.List;
import java.util.Optional;

public interface ChapitreService {
    List<Chapitre> getAllChapitres();
    Optional<Chapitre> getChapitreById(Long id);
    Chapitre createChapitre(Chapitre chapitre);
    Chapitre updateChapitre(Long id, Chapitre chapitreDetails);
    void deleteChapitre(Long id);
}