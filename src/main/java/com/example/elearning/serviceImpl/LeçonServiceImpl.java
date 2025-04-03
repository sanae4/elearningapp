package com.example.elearning.serviceImpl;

import com.example.elearning.dto.LeçonCreateUpdateDTO;
import com.example.elearning.dto.LeçonDTO;
import com.example.elearning.model.Course;
import com.example.elearning.model.Leçon;
import com.example.elearning.repository.CourseRepository;
import com.example.elearning.repository.LeçonRepository;
import com.example.elearning.service.LeçonService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeçonServiceImpl implements LeçonService {

    private final LeçonRepository leçonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public LeçonServiceImpl(LeçonRepository leçonRepository, CourseRepository courseRepository) {
        this.leçonRepository = leçonRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<LeçonDTO> getAllLeçons() {
        return leçonRepository.findByEstSuppriméFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LeçonDTO getLeçonById(Long id) {
        Leçon leçon = leçonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leçon non trouvée avec l'ID: " + id));
        return convertToDTO(leçon);
    }

    @Override
    @Transactional
    public LeçonDTO createLeçon(LeçonCreateUpdateDTO leçonDTO) {
        Leçon leçon = new Leçon();
        updateLeçonFromDTO(leçon, leçonDTO);
        Leçon savedLeçon = leçonRepository.save(leçon);
        return convertToDTO(savedLeçon);
    }

    @Override
    @Transactional
    public LeçonDTO updateLeçon(Long id, LeçonCreateUpdateDTO leçonDTO) {
        Leçon leçon = leçonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leçon non trouvée avec l'ID: " + id));

        updateLeçonFromDTO(leçon, leçonDTO);
        Leçon updatedLeçon = leçonRepository.save(leçon);
        return convertToDTO(updatedLeçon);
    }

    @Override
    @Transactional
    public void deleteLeçon(Long id) {
        if (!leçonRepository.existsById(id)) {
            throw new EntityNotFoundException("Leçon non trouvée avec l'ID: " + id);
        }
        leçonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LeçonDTO supprimerLeçon(Long id) {
        Leçon leçon = leçonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Leçon non trouvée avec l'ID: " + id));

        leçon.setEstSupprimé(true);
        Leçon updatedLeçon = leçonRepository.save(leçon);
        return convertToDTO(updatedLeçon);
    }


    @Override
    public List<LeçonDTO> getLeçonsByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new EntityNotFoundException("Cours non trouvé avec l'ID: " + courseId);
        }

        return leçonRepository.findByCourseId(courseId).stream()
                .filter(leçon -> !leçon.isEstSupprimé())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeçonDTO> searchLeçonsByTitle(String titre) {
        return leçonRepository.findByTitreLeçonContainingIgnoreCase(titre).stream()
                .filter(leçon -> !leçon.isEstSupprimé())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Méthodes utilitaires
    private LeçonDTO convertToDTO(Leçon leçon) {
        LeçonDTO dto = new LeçonDTO();
        dto.setId(leçon.getId());
        dto.setTitreLeçon(leçon.getTitreLeçon());
        dto.setDescription(leçon.getDescription());
        dto.setUseAI(leçon.isUseAI());

        dto.setNbrChapitres(leçon.getNbrChapitres());
        dto.setConseilsEnseignant(leçon.getConseilsEnseignant());
        dto.setEstSupprimé(leçon.isEstSupprimé());

        // Gestion du cours
        if (leçon.getCourse() != null) {
            dto.setCourseId(leçon.getCourse().getId());
            dto.setTitreCours(leçon.getCourse().getTitreCours());
        }

        return dto;
    }

    private void updateLeçonFromDTO(Leçon leçon, LeçonCreateUpdateDTO dto) {
        leçon.setTitreLeçon(dto.getTitreLeçon());
        leçon.setDescription(dto.getDescription());
        leçon.setNbrChapitres(dto.getNbrChapitres());
        leçon.setConseilsEnseignant(dto.getConseilsEnseignant());
        leçon.setEstSupprimé(dto.isEstSupprimé());
        leçon.setUseAI(dto.isUseAI()); // CETTE LIGNE MANQUAIT

        if (dto.getCourseId() != null) {
            Course course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'ID: " + dto.getCourseId()));
            leçon.setCourse(course);
        }
    }
    }

