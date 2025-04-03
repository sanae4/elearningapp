package com.example.elearning.serviceImpl;

import com.example.elearning.model.Etudiant;
import com.example.elearning.repository.EtudiantRepository;
import com.example.elearning.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).orElse(null);
    }

    @Override
    public Etudiant createEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant updateEtudiant(Long id, Etudiant etudiant) {
        etudiant.setId(id); // Assurez-vous que l'ID est correctement défini
        return etudiantRepository.save(etudiant);
    }

    @Override
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }
}