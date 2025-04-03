package com.example.elearning.repository;

import com.example.elearning.model.Leçon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeçonRepository extends JpaRepository<Leçon, Long> {

    // Trouver une leçon par son titre
    Optional<Leçon> findByTitreLeçon(String titreLeçon);

    // Trouver les leçons par l'ID du cours associé
    List<Leçon> findByCourseId(Long courseId);

    // Trouver les leçons qui ne sont pas supprimées
    List<Leçon> findByEstSuppriméFalse();

    // Rechercher des leçons par titre (recherche partielle, insensible à la casse)
    List<Leçon> findByTitreLeçonContainingIgnoreCase(String titreLeçon);

    // Vérifier si une leçon existe pour un cours donné
    boolean existsByCourseId(Long courseId);
}
