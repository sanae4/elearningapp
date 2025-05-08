package com.example.elearning.serviceImpl;

import com.example.elearning.dto.RapportDTO;
import com.example.elearning.model.Etudiant;
import com.example.elearning.model.Enseignant;
import com.example.elearning.model.Rapport;
import com.example.elearning.repository.RapportRepository;
import com.example.elearning.repository.EtudiantRepository;
import com.example.elearning.repository.EnseignantRepository;
import com.example.elearning.service.RapportService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RapportServiceImpl implements RapportService {

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    @Transactional
    public RapportDTO creerRapport(RapportDTO rapportDTO) {
        Rapport rapport = convertToEntity(rapportDTO);
        Rapport savedRapport = rapportRepository.save(rapport);
        return convertToDTO(savedRapport);
    }

    @Override
    @Transactional
    public RapportDTO creerRapportManuel(RapportDTO rapportDTO) {
        // Similaire à creerRapport, mais avec une logique supplémentaire si nécessaire
        Rapport rapport = convertToEntity(rapportDTO);
        rapport.creerManuel(); // Appel de la méthode spécifique
        Rapport savedRapport = rapportRepository.save(rapport);
        return convertToDTO(savedRapport);
    }

    @Override
    @Transactional
    public RapportDTO getRapportById(Long id) {
        Rapport rapport = rapportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rapport non trouvé avec l'ID: " + id));
        return convertToDTO(rapport);
    }

    @Override
    @Transactional
    public List<RapportDTO> getAllRapports() {
        List<Rapport> rapports = rapportRepository.findAll();
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RapportDTO updateRapport(Long id, RapportDTO rapportDTO) {
        if (!rapportRepository.existsById(id)) {
            throw new EntityNotFoundException("Rapport non trouvé avec l'ID: " + id);
        }

        Rapport rapport = convertToEntity(rapportDTO);
        rapport.setId(id); // S'assurer que l'ID est bien défini
        Rapport updatedRapport = rapportRepository.save(rapport);
        return convertToDTO(updatedRapport);
    }

    @Override
    @Transactional
    public void supprimerRapport(Long id) {
        if (!rapportRepository.existsById(id)) {
            throw new EntityNotFoundException("Rapport non trouvé avec l'ID: " + id);
        }
        rapportRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RapportDTO archiverRapport(Long id) {
        Rapport rapport = rapportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rapport non trouvé avec l'ID: " + id));
        rapport.setEstArchive(1);
        Rapport archivedRapport = rapportRepository.save(rapport);
        return convertToDTO(archivedRapport);
    }

    @Override
    @Transactional
    public RapportDTO desarchiverRapport(Long id) {
        Rapport rapport = rapportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rapport non trouvé avec l'ID: " + id));
        rapport.setEstArchive(0);
        Rapport unarchivedRapport = rapportRepository.save(rapport);
        return convertToDTO(unarchivedRapport);
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsArchives() {
        List<Rapport> rapports = rapportRepository.findByEstArchive(1);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsNonArchives() {
        List<Rapport> rapports = rapportRepository.findByEstArchive(0);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByTeacherId(Long teacherId) {
        List<Rapport> rapports = rapportRepository.findByEnseignantId(teacherId);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByStudentId(Long studentId) {
        List<Rapport> rapports = rapportRepository.findByEtudiantId(studentId);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByTeacherIdAndStudentId(Long teacherId, Long studentId) {
        List<Rapport> rapports = rapportRepository.findByTeacherIdAndStudentId(teacherId, studentId);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByTitreContaining(String titre) {
        List<Rapport> rapports = rapportRepository.findByTitreContaining(titre);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByDate(Date date) {
        List<Rapport> rapports = rapportRepository.findByDate(date);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsByDateRange(Date dateDebut, Date dateFin) {
        List<Rapport> rapports = rapportRepository.findByDateBetween(dateDebut, dateFin);
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long countRapportsByTeacherId(Long teacherId) {
        return rapportRepository.countByEnseignantId(teacherId);
    }

    @Override
    @Transactional
    public long countRapportsByStudentId(Long studentId) {
        return rapportRepository.countByStudentId(studentId);
    }

    @Override
    @Transactional
    public List<RapportDTO> getLatestRapports(int limit) {
        // Pour simplifier, on utilise findTop10ByOrderByDateDesc()
        // Pour une solution plus générique, il faudrait créer une méthode de repository avec un paramètre de limite
        List<Rapport> rapports = rapportRepository.findTop10ByOrderByDateDesc();
        return rapports.stream()
                .limit(limit)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RapportDTO> getRapportsWithMostStudents() {
        List<Rapport> rapports = rapportRepository.findRapportsWithMostStudents();
        return rapports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RapportDTO addStudentToRapport(Long rapportId, Long studentId) {
        Rapport rapport = rapportRepository.findById(rapportId)
                .orElseThrow(() -> new EntityNotFoundException("Rapport non trouvé avec l'ID: " + rapportId));

        Etudiant etudiant = etudiantRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + studentId));

        if (rapport.getEtudiants() == null) {
            rapport.setEtudiants(new ArrayList<>());
        }

        if (!rapport.getEtudiants().contains(etudiant)) {
            rapport.getEtudiants().add(etudiant);
            rapport = rapportRepository.save(rapport);
        }

        return convertToDTO(rapport);
    }

    @Override
    @Transactional
    public RapportDTO removeStudentFromRapport(Long rapportId, Long studentId) {
        Rapport rapport = rapportRepository.findById(rapportId)
                .orElseThrow(() -> new EntityNotFoundException("Rapport non trouvé avec l'ID: " + rapportId));

        Etudiant etudiant = etudiantRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé avec l'ID: " + studentId));

        if (rapport.getEtudiants() != null) {
            rapport.getEtudiants().remove(etudiant);
            rapport = rapportRepository.save(rapport);
        }

        return convertToDTO(rapport);
    }

    // Méthodes utilitaires pour conversion entité <-> DTO

    private Rapport convertToEntity(RapportDTO dto) {
        Rapport rapport = new Rapport();

        if (dto.getId() != null) {
            rapport.setId(dto.getId());
        }

        rapport.setTitre(dto.getTitre());
        rapport.setText(dto.getText());
        rapport.setEstArchive(dto.getEstArchive());
        rapport.setDate(dto.getDate());

        // Définir l'enseignant
        if (dto.getEnseignantId() != null) {
            Enseignant enseignant = enseignantRepository.findById(dto.getEnseignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + dto.getEnseignantId()));
            rapport.setEnseignant(enseignant);
        }

        // Définir les étudiants
        if (dto.getEtudiantIds() != null && !dto.getEtudiantIds().isEmpty()) {
            List<Etudiant> etudiants = etudiantRepository.findAllById(dto.getEtudiantIds());
            rapport.setEtudiants(etudiants);
        }

        return rapport;
    }

    private RapportDTO convertToDTO(Rapport rapport) {
        RapportDTO dto = new RapportDTO();

        dto.setId(rapport.getId());
        dto.setTitre(rapport.getTitre());
        dto.setText(rapport.getText());
        dto.setEstArchive(rapport.getEstArchive());
        dto.setDate(rapport.getDate());

        // Récupérer les informations de l'enseignant
        if (rapport.getEnseignant() != null) {
            dto.setEnseignantId(rapport.getEnseignant().getId());
            dto.setEnseignantNom(rapport.getEnseignant().getNom() + " " + rapport.getEnseignant().getPrenom());
        }

        // Récupérer les informations des étudiants
        if (rapport.getEtudiants() != null && !rapport.getEtudiants().isEmpty()) {
            dto.setEtudiantIds(rapport.getEtudiants().stream()
                    .map(Etudiant::getId)
                    .collect(Collectors.toList()));

            dto.setEtudiantNoms(rapport.getEtudiants().stream()
                    .map(e -> e.getNom() + " " + e.getPrenom())
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
