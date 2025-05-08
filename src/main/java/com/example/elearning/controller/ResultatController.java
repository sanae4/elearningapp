package com.example.elearning.controller;

import com.example.elearning.dto.ResultatDTO;
import com.example.elearning.service.ResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultat")
@CrossOrigin(origins = "*")
public class ResultatController {

    private final ResultatService resultatService;

    @Autowired
    public ResultatController(ResultatService resultatService) {
        this.resultatService = resultatService;
    }


    @GetMapping
    public ResponseEntity<List<ResultatDTO>> getAllResultats() {
        List<ResultatDTO> resultats = resultatService.getAllResultats();
        return ResponseEntity.ok(resultats);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResultatDTO> getResultatById(@PathVariable Long id) {
        ResultatDTO resultat = resultatService.getResultatById(id);
        if (resultat != null) {
            return ResponseEntity.ok(resultat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<ResultatDTO> getResultatByQuizId(@PathVariable Long quizId) {
        ResultatDTO resultat = resultatService.getResultatByQuizId(quizId);
        if (resultat != null) {
            return ResponseEntity.ok(resultat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/reussis")
    public ResponseEntity<List<ResultatDTO>> getResultatsReussis() {
        List<ResultatDTO> resultats = resultatService.getResultatsReussis();
        return ResponseEntity.ok(resultats);
    }


    @GetMapping("/echoues")
    public ResponseEntity<List<ResultatDTO>> getResultatsEchoues() {
        List<ResultatDTO> resultats = resultatService.getResultatsEchoues();
        return ResponseEntity.ok(resultats);
    }


    @PostMapping
    public ResponseEntity<ResultatDTO> createResultat(@RequestBody ResultatDTO resultatDTO) {
        ResultatDTO createdResultat = resultatService.createResultat(resultatDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResultat);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResultatDTO> updateResultat(@PathVariable Long id, @RequestBody ResultatDTO resultatDTO) {
        ResultatDTO updatedResultat = resultatService.updateResultat(id, resultatDTO);
        if (updatedResultat != null) {
            return ResponseEntity.ok(updatedResultat);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        resultatService.deleteResultat(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{resultatId}/calculer-reussite")
    public ResponseEntity<Boolean> calculerReussite(@PathVariable Long resultatId, @RequestParam int scoreMinimum) {
        boolean reussite = resultatService.calculerReussite(resultatId, scoreMinimum);
        return ResponseEntity.ok(reussite);
    }


    @GetMapping("/{resultatId}/rapport")
    public ResponseEntity<String> genererRapport(@PathVariable Long resultatId) {
        String rapport = resultatService.genererRapport(resultatId);
        return ResponseEntity.ok(rapport);
    }


    @GetMapping("/quiz/{quizId}/score-moyen")
    public ResponseEntity<Double> calculerScoreMoyen(@PathVariable Long quizId) {
        Double scoreMoyen = resultatService.calculerScoreMoyen(quizId);
        if (scoreMoyen != null) {
            return ResponseEntity.ok(scoreMoyen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
