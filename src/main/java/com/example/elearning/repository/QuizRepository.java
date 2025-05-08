package com.example.elearning.repository;

import com.example.elearning.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /**
     * Trouve tous les quiz associés à un cours spécifique
     * @param courseId ID du cours
     * @return Liste des quiz du cours
     */
    List<Quiz> findByCourseId(Long courseId);

    /**
     * Trouve tous les quiz qui ne sont pas supprimés
     * @return Liste des quiz actifs
     */
    List<Quiz> findByEstsupprimerFalse();

    /**
     * Trouve tous les quiz actifs d'un cours spécifique
     * @param courseId ID du cours
     * @return Liste des quiz actifs du cours
     */
    List<Quiz> findByCourseIdAndEstsupprimerFalse(Long courseId);

    /**
     * Recherche des quiz par titre (recherche partielle, insensible à la casse)
     * @param titre Titre à rechercher
     * @return Liste des quiz correspondants
     */
    @Query("SELECT q FROM Quiz q WHERE LOWER(q.titre) LIKE LOWER(CONCAT('%', :titre, '%'))")
    List<Quiz> searchByTitre(@Param("titre") String titre);
}
