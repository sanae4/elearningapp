package com.example.elearning.repository;

import com.example.elearning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Trouver tous les cours d'une catégorie spécifique
    List<Course> findByCategoryId(Long categoryId);

    // Trouver tous les cours d'un enseignant spécifique
    List<Course> findByEnseignantId(Long enseignantId);

    // Rechercher des cours par titre
    List<Course> findByTitreCoursContainingIgnoreCase(String titre);

    // Trouver les cours par niveau
    List<Course> findByCourselevel(String level);

    // Trouver les cours par langue
    List<Course> findByLangage(String langage);
}
