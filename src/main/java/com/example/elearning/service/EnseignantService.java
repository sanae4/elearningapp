package com.example.elearning.service;

import com.example.elearning.dto.EnseignantDTO;
import com.example.elearning.model.Enseignant;
import java.util.List;

public interface EnseignantService {
    List<EnseignantDTO> getPendingTeachers();
    EnseignantDTO approveTeacher(Long id);
    EnseignantDTO getTeacherById(Long id);
    List<EnseignantDTO> getAllTeachers();
    List<Enseignant> getAllEnseignants();

    Enseignant getEnseignantById(Long id);

    Enseignant createEnseignant(Enseignant enseignant);

    Enseignant updateEnseignant(Long id, Enseignant enseignant);

    void deleteEnseignant(Long id);

    Enseignant saveEnseignant(Enseignant enseignant);
    Enseignant updateEnseignant(Long id, EnseignantDTO enseignantDTO);
}