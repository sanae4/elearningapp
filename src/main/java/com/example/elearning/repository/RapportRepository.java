package com.example.elearning.repository;

import com.example.elearning.model.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {

    // Recherche par statut d'archivage
    List<Rapport> findByEstArchive(int estArchive);

    // Recherche par enseignant (correction du nom de la méthode)
    List<Rapport> findByEnseignantId(Long enseignantId);

    // Recherche par étudiant (correction du nom de la méthode)
    @Query("SELECT r FROM Rapport r JOIN r.etudiants e WHERE e.id = :etudiantId")
    List<Rapport> findByEtudiantId(@Param("etudiantId") Long etudiantId);

    // Recherche par titre (contenant)
    List<Rapport> findByTitreContaining(String titre);

    // Recherche par date
    List<Rapport> findByDate(Date date);

    // Recherche par plage de dates
    List<Rapport> findByDateBetween(Date dateDebut, Date dateFin);

    // Requête personnalisée pour rechercher des rapports par mot-clé dans le titre ou le texte
    @Query("SELECT r FROM Rapport r WHERE LOWER(r.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(r.text) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Rapport> searchByKeyword(@Param("keyword") String keyword);

    // Requête personnalisée pour obtenir les rapports d'un enseignant qui concernent un étudiant spécifique
    @Query("SELECT r FROM Rapport r JOIN r.etudiants e WHERE r.enseignant.id = :teacherId AND e.id = :studentId")
    List<Rapport> findByTeacherIdAndStudentId(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId);

    // Compter le nombre de rapports créés par un enseignant
    long countByEnseignantId(Long enseignantId);

    // Compter le nombre de rapports concernant un étudiant
    @Query("SELECT COUNT(r) FROM Rapport r JOIN r.etudiants e WHERE e.id = :studentId")
    long countByStudentId(@Param("studentId") Long studentId);

    // Trouver les derniers rapports créés (limité à un certain nombre)
    List<Rapport> findTop10ByOrderByDateDesc();

    // Trouver les rapports avec le plus grand nombre d'étudiants concernés
    @Query("SELECT r FROM Rapport r LEFT JOIN r.etudiants e GROUP BY r.id ORDER BY COUNT(e.id) DESC")
    List<Rapport> findRapportsWithMostStudents();
}
