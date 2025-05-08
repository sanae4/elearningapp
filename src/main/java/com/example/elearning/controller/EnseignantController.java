package com.example.elearning.controller;

import com.example.elearning.dto.EnseignantDTO;
import com.example.elearning.model.Enseignant;
import com.example.elearning.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignant")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @PostMapping("/register")
    public ResponseEntity<Enseignant> registerEnseignant(@RequestBody Enseignant enseignant) {
        Enseignant savedEnseignant = enseignantService.saveEnseignant(enseignant);
        return ResponseEntity.ok(savedEnseignant);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EnseignantDTO>> getPendingTeachers() {
        List<EnseignantDTO> pendingTeachers = enseignantService.getPendingTeachers();
        return ResponseEntity.ok(pendingTeachers);
    }

    // Récupérer tous les enseignants
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<EnseignantDTO>> getAllTeachers() {
        List<EnseignantDTO> allTeachers = enseignantService.getAllTeachers();
        return ResponseEntity.ok(allTeachers);
    }

    // Récupérer un enseignant spécifique
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnseignantDTO> getTeacherById(@PathVariable Long id) {
        EnseignantDTO teacher = enseignantService.getTeacherById(id);
        return ResponseEntity.ok(teacher);
    }

    // Approuver un enseignant
    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EnseignantDTO> approveTeacher(@PathVariable Long id) {
        EnseignantDTO approvedTeacher = enseignantService.approveTeacher(id);
        return ResponseEntity.ok(approvedTeacher);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<EnseignantDTO> updateTeacher(
            @PathVariable Long id,
            @RequestBody EnseignantDTO enseignantDTO) {

        // Convertir DTO en entité ou mettre à jour directement via service
        Enseignant updatedEnseignant = enseignantService.updateEnseignant(id, enseignantDTO);

        // Convertir l'entité mise à jour en DTO
        EnseignantDTO updatedTeacherDTO = enseignantService.getTeacherById(id);

        return ResponseEntity.ok(updatedTeacherDTO);
    }


    // Supprimer un enseignant
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }
}