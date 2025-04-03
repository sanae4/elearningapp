package com.example.elearning.controller;

import com.example.elearning.model.Enseignant;
import com.example.elearning.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @PostMapping("/register")
    public ResponseEntity<Enseignant> registerEnseignant(@RequestBody Enseignant enseignant) {
        Enseignant savedEnseignant = enseignantService.saveEnseignant(enseignant);
        return ResponseEntity.ok(savedEnseignant);
    }

    // Ajoutez d'autres endpoints selon vos besoins (par exemple, pour obtenir un enseignant par ID)
}