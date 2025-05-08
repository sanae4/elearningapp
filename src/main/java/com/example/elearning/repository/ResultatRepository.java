package com.example.elearning.repository;

import com.example.elearning.model.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    Optional<Resultat> findByQuizId(Long quizId);

    List<Resultat> findByestpasseTrue();


    List<Resultat> findByestpasseFalse();


    @Query("SELECT r FROM Resultat r WHERE r.score >= :scoreMinimum")
    List<Resultat> findByScoreGreaterThanEqual(@Param("scoreMinimum") int scoreMinimum);

    @Query("SELECT AVG(r.score) FROM Resultat r WHERE r.quiz.id = :quizId")
    Double calculateAverageScoreByQuizId(@Param("quizId") Long quizId);
}
