package com.example.elearning.serviceImpl;

import com.example.elearning.dto.ChapitreDTO;
import com.example.elearning.model.Chapitre;
import com.example.elearning.model.Leçon;
import com.example.elearning.repository.ChapitreRepository;
import com.example.elearning.repository.LeçonRepository;
import com.example.elearning.service.ChapitreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapitreServiceImpl implements ChapitreService {

    @Autowired
    private ChapitreRepository chapitreRepository;

    @Autowired
    private LeçonRepository leçonRepository;

    // Convert Entity to DTO
    private ChapitreDTO convertToDto(Chapitre chapitre) {
        ChapitreDTO chapitreDTO = new ChapitreDTO();
        chapitreDTO.setId(chapitre.getId());
        chapitreDTO.setResumer(chapitre.getResumer());
        chapitreDTO.setTitre(chapitre.getTitre());
        chapitreDTO.setType(chapitre.getType());
        chapitreDTO.setContenu(chapitre.getContenu());
        if (chapitre.getLecon() != null) {
            chapitreDTO.setLeconId(chapitre.getLecon().getId());
        }
        return chapitreDTO;
    }

    // Convert DTO to Entity
    private Chapitre convertToEntity(ChapitreDTO chapitreDTO) {
        Chapitre chapitre = new Chapitre();
        chapitre.setId(chapitreDTO.getId());
        chapitre.setResumer(chapitreDTO.getResumer());
        chapitre.setTitre(chapitreDTO.getTitre());
        chapitre.setType(chapitreDTO.getType());
        chapitre.setContenu(chapitreDTO.getContenu());

        if (chapitreDTO.getLeconId() != null) {
            Leçon leçon = leçonRepository.findById(chapitreDTO.getLeconId())
                    .orElseThrow(() -> new RuntimeException("Leçon not found"));
            chapitre.setLecon(leçon);
        }
        return chapitre;
    }

    @Override
    public List<ChapitreDTO> getChapitresByLeconId(Long leconId) {
        return chapitreRepository.findByLeconId(leconId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<ChapitreDTO> getChapitreById(Long id) {
        return chapitreRepository.findById(id)
                .map(this::convertToDto);
    }

    @Override
    public ChapitreDTO createChapitre(ChapitreDTO chapitreDTO) {
        Chapitre chapitre = convertToEntity(chapitreDTO);
        Chapitre savedChapitre = chapitreRepository.save(chapitre);
        return convertToDto(savedChapitre);
    }

    @Override
    public ChapitreDTO updateChapitre(Long id, ChapitreDTO chapitreDTO) {
        Chapitre existingChapitre = chapitreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));

        existingChapitre.setResumer(chapitreDTO.getResumer());
        existingChapitre.setTitre(chapitreDTO.getTitre());
        existingChapitre.setType(chapitreDTO.getType());
        existingChapitre.setContenu(chapitreDTO.getContenu());

        if (chapitreDTO.getLeconId() != null &&
                (existingChapitre.getLecon() == null ||
                        !existingChapitre.getLecon().getId().equals(chapitreDTO.getLeconId()))) {
            Leçon leçon = leçonRepository.findById(chapitreDTO.getLeconId())
                    .orElseThrow(() -> new RuntimeException("Leçon not found"));
            existingChapitre.setLecon(leçon);
        }

        Chapitre updatedChapitre = chapitreRepository.save(existingChapitre);
        return convertToDto(updatedChapitre);
    }

    @Override
    public void deleteChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }
}