package com.example.elearning.controller;

import com.example.elearning.model.Adresse;
import com.example.elearning.service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adresses")
public class AdresseController {

    @Autowired
    private AdresseService adresseService;

    // Créer une adresse
    @PostMapping
    public ResponseEntity<Adresse> createAdresse(@RequestBody Adresse adresse) {
        Adresse savedAdresse = adresseService.saveAdresse(adresse);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdresse);
    }

    // Récupérer une adresse par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Adresse> getAdresseById(@PathVariable Long id) {
        Adresse adresse = adresseService.getAdresseById(id);
        return ResponseEntity.ok(adresse);
    }

    // Récupérer toutes les adresses
    @GetMapping
    public ResponseEntity<List<Adresse>> getAllAdresses() {
        List<Adresse> adresses = adresseService.getAllAdresses();
        return ResponseEntity.ok(adresses);
    }

    // Mettre à jour une adresse
    @PutMapping("/{id}")
    public ResponseEntity<Adresse> updateAdresse(@PathVariable Long id, @RequestBody Adresse adresse) {
        Adresse updatedAdresse = adresseService.updateAdresse(id, adresse);
        return ResponseEntity.ok(updatedAdresse);
    }

    // Supprimer une adresse
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long id) {
        adresseService.deleteAdresse(id);
        return ResponseEntity.noContent().build();
    }
}