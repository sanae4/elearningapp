package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;
    private boolean estpasse;

    // Relation avec Quiz (un résultat appartient à un quiz)
    @OneToOne
    @JoinColumn(name = "quiz_id")
    @JsonBackReference("quiz-resultat")
    private Quiz quiz;

    /**
     * Calcule si le quiz est réussi en fonction du score
     * @param scoreMinimum Score minimum pour réussir (en pourcentage)
     * @return true si le quiz est réussi, false sinon
     */
    public boolean calculerReussite(int scoreMinimum) {
        this.estpasse = this.score >= scoreMinimum;
        return this.estpasse;
    }

    public String genererRapport() {
        StringBuilder rapport = new StringBuilder();
        rapport.append("Résultat du quiz: ").append(this.quiz.getTitre()).append("\n");
        rapport.append("Score: ").append(this.score).append("%\n");
        rapport.append("Statut: ").append(this.estpasse ? "Réussi" : "Échoué").append("\n");

        // On pourrait ajouter plus de détails ici, comme les questions réussies/échouées

        return rapport.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resultat resultat = (Resultat) o;
        return Objects.equals(id, resultat.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
