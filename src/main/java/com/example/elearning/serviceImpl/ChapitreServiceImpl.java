package com.example.elearning.service;

import com.example.elearning.model.Chapitre;
import com.example.elearning.repository.ChapitreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapitreServiceImpl implements ChapitreService {

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Override
    public List<Chapitre> getAllChapitres() {
        return chapitreRepository.findAll();
    }

    @Override
    public Optional<Chapitre> getChapitreById(Long id) {
        return chapitreRepository.findById(id);
    }

    @Override
    public Chapitre createChapitre(Chapitre chapitre) {
        return chapitreRepository.save(chapitre);
    }

    @Override
    public Chapitre updateChapitre(Long id, Chapitre chapitreDetails) {
        Chapitre chapitre = chapitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));
        chapitre.setTitre(chapitreDetails.getTitre());
        chapitre.setType(chapitreDetails.getType());
        chapitre.setContenu(chapitreDetails.getContenu());
        chapitre.setLeçon(chapitreDetails.getLeçon());
        return chapitreRepository.save(chapitre);
    }

    @Override
    public void deleteChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }
}