package com.example.elearning.repository;

import com.example.elearning.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * Trouve toutes les questions associées à un quiz spécifique
     * @param quizId ID du quiz
     * @return Liste des questions du quiz
     */
    List<Question> findByQuizId(Long quizId);

    /**
     * Trouve toutes les questions d'un type spécifique
     * @param type Type de question (QCM, Vrai/Faux, Générative, etc.)
     * @return Liste des questions du type spécifié
     */
    List<Question> findByType(String type);

    /**
     * Trouve toutes les questions d'un type spécifique dans un quiz
     * @param quizId ID du quiz
     * @param type Type de question
     * @return Liste des questions correspondantes
     */
    List<Question> findByQuizIdAndType(Long quizId, String type);

    /**
     * Recherche des questions par texte (recherche partielle, insensible à la casse)
     * @param texte Texte à rechercher
     * @return Liste des questions correspondantes
     */
    @Query("SELECT q FROM Question q WHERE LOWER(q.texte) LIKE LOWER(CONCAT('%', :texte, '%'))")
    List<Question> searchByTexte(@Param("texte") String texte);
}
