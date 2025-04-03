package com.example.elearning.controller;

import com.example.elearning.dto.LeçonCreateUpdateDTO;
import com.example.elearning.dto.LeçonDTO;
import com.example.elearning.service.LeçonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecons")
public class LeçonController {

    private final LeçonService leçonService;

    @Autowired
    public LeçonController(LeçonService leçonService) {
        this.leçonService = leçonService;
    }

    @GetMapping
    public ResponseEntity<List<LeçonDTO>> getAllLeçons() {
        return ResponseEntity.ok(leçonService.getAllLeçons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeçonDTO> getLeçonById(@PathVariable Long id) {
        return ResponseEntity.ok(leçonService.getLeçonById(id));
    }

    @PostMapping
    public ResponseEntity<LeçonDTO> createLeçon(@RequestBody LeçonCreateUpdateDTO leçonDTO) {
        return new ResponseEntity<>(leçonService.createLeçon(leçonDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeçonDTO> updateLeçon(@PathVariable Long id, @RequestBody LeçonCreateUpdateDTO leçonDTO) {
        return ResponseEntity.ok(leçonService.updateLeçon(id, leçonDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeçon(@PathVariable Long id) {
        leçonService.deleteLeçon(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/supprimer")
    public ResponseEntity<LeçonDTO> supprimerLeçon(@PathVariable Long id) {
        return ResponseEntity.ok(leçonService.supprimerLeçon(id));
    }



    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LeçonDTO>> getLeçonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(leçonService.getLeçonsByCourse(courseId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<LeçonDTO>> searchLeçonsByTitle(@RequestParam String titre) {
        return ResponseEntity.ok(leçonService.searchLeçonsByTitle(titre));
    }
}
