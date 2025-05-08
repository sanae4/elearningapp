package com.example.elearning.controller;

import com.example.elearning.dto.ChapitreDTO;
import com.example.elearning.service.ChapitreService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/chapitre")
@CrossOrigin(origins = "*")
public class ChapitreController {

    @Autowired
    private ChapitreService chapitreService;
    @Autowired
    private ObjectMapper objectMapper;
    // Endpoint existant pour récupérer un chapitre
    @GetMapping("/{id}")
    public ChapitreDTO getChapitreById(@PathVariable Long id) {
        return chapitreService.getChapitreById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));
    }

    // Endpoint existant pour récupérer les chapitres par leçon
    @GetMapping("/byLecon/{leconId}")
    public ResponseEntity<List<ChapitreDTO>> getChapitresByLecon(@PathVariable Long leconId) {
        List<ChapitreDTO> chapitres = chapitreService.getChapitresByLeconId(leconId);
        return ResponseEntity.ok(chapitres);
    }

    // Nouveau endpoint pour créer un chapitre avec fichier
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChapitreDTO> createChapitre(
            @RequestPart("chapitre") String chapitreJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) throws IOException {

        // Parse JSON manually to handle the lessonId
        ObjectMapper objectMapper = new ObjectMapper();
        ChapitreDTO chapitreDTO = objectMapper.readValue(chapitreJson, ChapitreDTO.class);

        if (file != null && !file.isEmpty()) {
            chapitreDTO.setContenu(file.getBytes());
            chapitreDTO.setType(file.getContentType());
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            chapitreDTO.setContenu(videoFile.getBytes());
            chapitreDTO.setType(videoFile.getContentType());
        }

        ChapitreDTO createdChapitre = chapitreService.createChapitre(chapitreDTO);
        return ResponseEntity.ok(createdChapitre);
    }

    // Endpoint pour mettre à jour un chapitre avec fichier
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChapitreDTO> updateChapitre(
            @PathVariable Long id,
            @RequestPart("chapitre") String chapitreJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "videoFile", required = false) MultipartFile videoFile) throws IOException {

        // Parse JSON pour obtenir le DTO
        ChapitreDTO chapitreDTO = objectMapper.readValue(chapitreJson, ChapitreDTO.class);

        // Si un nouveau fichier est fourni, mettez à jour le contenu
        if (file != null && !file.isEmpty()) {
            chapitreDTO.setContenu(file.getBytes());
            chapitreDTO.setType("TEXT");
        }

        if (videoFile != null && !videoFile.isEmpty()) {
            chapitreDTO.setContenu(videoFile.getBytes());
            chapitreDTO.setType("VIDEO");
        }

        ChapitreDTO updatedChapitre = chapitreService.updateChapitre(id, chapitreDTO);
        return ResponseEntity.ok(updatedChapitre);
    }
    // Endpoint existant pour supprimer un chapitre
    @DeleteMapping("/{id}")
    public void deleteChapitre(@PathVariable Long id) {
        chapitreService.deleteChapitre(id);
    }
}
