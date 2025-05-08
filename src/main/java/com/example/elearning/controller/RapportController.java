package com.example.elearning.controller;


import com.example.elearning.dto.RapportDTO;
import com.example.elearning.serviceImpl.RapportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {

    @Autowired
    private RapportServiceImpl rapportService;




    @PostMapping
    public ResponseEntity<RapportDTO> creerRapport(@RequestBody RapportDTO rapportDTO) {
        return new ResponseEntity<>(rapportService.creerRapport(rapportDTO), HttpStatus.CREATED);
    }

    @PostMapping("/manuel")
    public ResponseEntity<RapportDTO> creerRapportManuel(@RequestBody RapportDTO rapportDTO) {
        return new ResponseEntity<>(rapportService.creerRapportManuel(rapportDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RapportDTO> getRapportById(@PathVariable Long id) {
        return ResponseEntity.ok(rapportService.getRapportById(id));
    }

    @GetMapping
    public ResponseEntity<List<RapportDTO>> getAllRapports() {
        return ResponseEntity.ok(rapportService.getAllRapports());
    }

    @GetMapping("/non-archives")
    public ResponseEntity<List<RapportDTO>> getRapportsNonArchives() {
        return ResponseEntity.ok(rapportService.getRapportsNonArchives());
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<RapportDTO>> getRapportsByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok(rapportService.getRapportsByTeacherId(teacherId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RapportDTO>> getRapportsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(rapportService.getRapportsByStudentId(studentId));
    }

    @PutMapping("/archiver/{id}")
    public ResponseEntity<RapportDTO> archiverRapport(@PathVariable Long id) {
        return ResponseEntity.ok(rapportService.archiverRapport(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerRapport(@PathVariable Long id) {
        rapportService.supprimerRapport(id);
        return ResponseEntity.noContent().build();
    }
}