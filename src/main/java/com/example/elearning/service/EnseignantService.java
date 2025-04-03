package com.example.elearning.service;

import com.example.elearning.model.Enseignant;
import java.util.List;

public interface EnseignantService {
    List<Enseignant> getAllEnseignants();

    Enseignant getEnseignantById(Long id);

    Enseignant createEnseignant(Enseignant enseignant);

    Enseignant updateEnseignant(Long id, Enseignant enseignant);

    void deleteEnseignant(Long id);

    Enseignant saveEnseignant(Enseignant enseignant);

}