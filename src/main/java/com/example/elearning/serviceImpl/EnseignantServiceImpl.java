package com.example.elearning.serviceImpl;
import com.example.elearning.dto.EnseignantDTO;
import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.Enseignant;
import com.example.elearning.repository.EnseignantRepository;
import com.example.elearning.service.EnseignantService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnseignantServiceImpl implements EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public List<EnseignantDTO> getPendingTeachers() {
        // Récupérer tous les enseignants avec status = false
        List<Enseignant> pendingTeachers = enseignantRepository.findByStatusFalse();
        return pendingTeachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnseignantDTO approveTeacher(Long id) {
        // Récupérer l'enseignant par son ID
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id));

        // Changer son statut à true (approuvé)
        enseignant.setStatus(true);

        // Sauvegarder les changements
        Enseignant approvedTeacher = enseignantRepository.save(enseignant);

        // Retourner l'enseignant mis à jour
        return convertToDTO(approvedTeacher);
    }

    @Override
    @Transactional
    public EnseignantDTO getTeacherById(Long id) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id));
        return convertToDTO(enseignant);
    }

    @Override
    @Transactional
    public List<EnseignantDTO> getAllTeachers() {
        List<Enseignant> allTeachers = enseignantRepository.findAll();
        return allTeachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Implémentation des méthodes manquantes
    @Override
    @Transactional
    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    @Override
    @Transactional
    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id));
    }

    @Override
    @Transactional
    public Enseignant createEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }


    @Override
    @Transactional
    public Enseignant updateEnseignant(Long id, Enseignant enseignant) {
        // Convertir Enseignant en EnseignantDTO
        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setNom(enseignant.getNom());
        dto.setPrenom(enseignant.getPrenom());
        dto.setEmail(enseignant.getEmail());
        dto.setNumtele(enseignant.getNumtele());
        dto.setBiographie(enseignant.getBiographie());
        dto.setSpecialite(enseignant.getSpecialite());
        dto.setAnneesExperience(enseignant.getAnneesExperience());
        dto.setStatus(enseignant.isStatus());
        dto.setDatenaissance(enseignant.getDatenaissance());
        dto.setGenre(enseignant.getGenre());
        dto.setAdresse(enseignant.getAdresse());
        dto.setRole(enseignant.getRole());

        // Utiliser la méthode existante qui accepte un EnseignantDTO
        return updateEnseignant(id, dto);
    }

    @Override
    @Transactional
    public Enseignant updateEnseignant(Long id, EnseignantDTO dto) {
        // 1. Récupérer l'enseignant existant
        Enseignant existingEnseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enseignant non trouvé avec l'ID: " + id));

        // 2. Mettre à jour les propriétés communes de User
        updateUserProperties(existingEnseignant, dto);

        // 3. Mettre à jour les propriétés spécifiques à Enseignant
        updateEnseignantProperties(existingEnseignant, dto);

        // 4. Enregistrer et retourner l'entité mise à jour
        return enseignantRepository.save(existingEnseignant);
    }

    /**
     * Met à jour les propriétés de base de l'utilisateur
     */
    private void updateUserProperties(Enseignant enseignant, EnseignantDTO dto) {
        // Propriétés obligatoires
        if (dto.getNom() != null) {
            enseignant.setNom(dto.getNom());
        }

        if (dto.getPrenom() != null) {
            enseignant.setPrenom(dto.getPrenom());
        }

        if (dto.getEmail() != null) {
            enseignant.setEmail(dto.getEmail());
        }

        // Propriétés optionnelles
        if (dto.getNumtele() != null) {
            enseignant.setNumtele(dto.getNumtele());
        }

        if (dto.getRole() != null) {
            enseignant.setRole(dto.getRole());
        }

        if (dto.getDatenaissance() != null) {
            enseignant.setDatenaissance(dto.getDatenaissance());
        }

        if (dto.getGenre() != null) {
            enseignant.setGenre(dto.getGenre());
        }

        // Gestion du mot de passe
        if (dto.getMotDePasse() != null && !dto.getMotDePasse().trim().isEmpty()) {
            enseignant.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        }

        // Mise à jour de l'adresse si fournie
        if (dto.getAdresse() != null) {
            enseignant.setAdresse(dto.getAdresse());
        }
    }

    /**
     * Met à jour les propriétés spécifiques à l'enseignant
     */
    private void updateEnseignantProperties(Enseignant enseignant, EnseignantDTO dto) {
        if (dto.getBiographie() != null) {
            enseignant.setBiographie(dto.getBiographie());
        }

        // La propriété status est un boolean primitif, donc pas besoin de vérifier null
        enseignant.setStatus(dto.isStatus());

        if (dto.getSpecialite() != null) {
            enseignant.setSpecialite(dto.getSpecialite());
        }

        if (dto.getAnneesExperience() != null) {
            enseignant.setAnneesExperience(dto.getAnneesExperience());
        }
    }
    @Override
    @Transactional
    public void deleteEnseignant(Long id) {
        // Vérifier si l'enseignant existe
        if (!enseignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + id);
        }
        enseignantRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Enseignant saveEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    // Méthode utilitaire pour convertir Enseignant en EnseignantDTO
    private EnseignantDTO convertToDTO(Enseignant enseignant) {
        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setNom(enseignant.getNom());
        dto.setPrenom(enseignant.getPrenom());
        dto.setEmail(enseignant.getEmail());
        dto.setNumtele(enseignant.getNumtele());
        dto.setBiographie(enseignant.getBiographie());
        dto.setSpecialite(enseignant.getSpecialite());
        dto.setAnneesExperience(enseignant.getAnneesExperience());
        dto.setStatus(enseignant.isStatus());

        // Inclure d'autres informations pertinentes
        if (enseignant.getDatenaissance() != null) {
            dto.setDatenaissance(enseignant.getDatenaissance());
        }

        dto.setGenre(enseignant.getGenre());

        // Inclure l'adresse si disponible
        if (enseignant.getAdresse() != null) {
            dto.setAdresse(enseignant.getAdresse());
        }

        return dto;
    }

}
