package com.example.elearning.controller;

import com.example.elearning.model.Chapitre;
import com.example.elearning.service.ChapitreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chapitre")
public class ChapitreController {

    @Autowired
    private ChapitreService chapitreService;

    @GetMapping
    public List<Chapitre> getAllChapitres() {
        return chapitreService.getAllChapitres();
    }

    @GetMapping("/{id}")
    public Chapitre getChapitreById(@PathVariable Long id) {
        return chapitreService.getChapitreById(id)
                .orElseThrow(() -> new RuntimeException("Chapitre not found"));
    }

    @PostMapping
    public Chapitre createChapitre(@RequestBody Chapitre chapitre) {
        return chapitreService.createChapitre(chapitre);
    }

    @PutMapping("/{id}")
    public Chapitre updateChapitre(@PathVariable Long id, @RequestBody Chapitre chapitreDetails) {
        return chapitreService.updateChapitre(id, chapitreDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteChapitre(@PathVariable Long id) {
        chapitreService.deleteChapitre(id);
    }
}