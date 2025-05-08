package com.example.elearning.service;


import com.example.elearning.dto.RapportDTO;
import org.hibernate.query.Page;

import java.util.Date;
import java.util.List;

public interface RapportService {

    // Opérations CRUD de base
    RapportDTO creerRapport(RapportDTO rapportDTO);
    RapportDTO creerRapportManuel(RapportDTO rapportDTO);
    RapportDTO getRapportById(Long id);
    List<RapportDTO> getAllRapports();
    RapportDTO updateRapport(Long id, RapportDTO rapportDTO);
    void supprimerRapport(Long id);

    // Opérations d'archivage
    RapportDTO archiverRapport(Long id);
    RapportDTO desarchiverRapport(Long id);
    List<RapportDTO> getRapportsArchives();
    List<RapportDTO> getRapportsNonArchives();

    // Recherche par relations
    List<RapportDTO> getRapportsByTeacherId(Long teacherId);
    List<RapportDTO> getRapportsByStudentId(Long studentId);
    List<RapportDTO> getRapportsByTeacherIdAndStudentId(Long teacherId, Long studentId);

    // Recherche avancée
    List<RapportDTO> getRapportsByTitreContaining(String titre);
    List<RapportDTO> getRapportsByDate(Date date);
    List<RapportDTO> getRapportsByDateRange(Date dateDebut, Date dateFin);



    // Statistiques
    long countRapportsByTeacherId(Long teacherId);
    long countRapportsByStudentId(Long studentId);

    // Opérations spéciales
    List<RapportDTO> getLatestRapports(int limit);
    List<RapportDTO> getRapportsWithMostStudents();

    // Gestion des étudiants associés à un rapport
    RapportDTO addStudentToRapport(Long rapportId, Long studentId);
    RapportDTO removeStudentFromRapport(Long rapportId, Long studentId);
}

