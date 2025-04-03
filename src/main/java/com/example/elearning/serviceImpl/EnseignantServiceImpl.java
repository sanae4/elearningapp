package com.example.elearning.serviceImpl;

import com.example.elearning.model.Enseignant;
import com.example.elearning.repository.EnseignantRepository;
import com.example.elearning.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Override
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }


    @Override
    public Enseignant saveEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }
    @Override
    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).orElse(null);
    }

    @Override
    public Enseignant createEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    @Override
    public Enseignant updateEnseignant(Long id, Enseignant enseignant) {
        enseignant.setId(id); // Assurez-vous que l'ID est correctement défini
        return enseignantRepository.save(enseignant);
    }

    @Override
    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
}