package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String texte;

    private String type; // QCM, Vrai/Faux, Générative, etc.

    @Column(columnDefinition = "TEXT")
    private String options; // Options séparées par des virgules ou format JSON

    @Column(columnDefinition = "TEXT")
    private String reponse_correct; // Format JSON pour supporter différents types de réponses

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")  // Cette colonne stockera l'ID du quiz
    @JsonBackReference("quiz-questions")
    private Quiz quiz;

    public boolean verifierReponse(String reponse) {
        // Logique de vérification selon le type de question
        switch (this.type.toLowerCase()) {
            case "vrai/faux":
                // Pour les questions vrai/faux, comparer directement
                return this.reponse_correct.contains(reponse.toLowerCase());

            case "qcm":
                // Pour les QCM, vérifier si la réponse est dans les options correctes
                return this.reponse_correct.contains(reponse);

            case "générative":
                // Pour les questions génératives, une logique plus complexe serait nécessaire
                // Ici, on pourrait implémenter une vérification basique de mots-clés
                String[] keywords = this.reponse_correct.replaceAll("[{}\"]", "")
                        .split(",");
                for (String keyword : keywords) {
                    if (!reponse.toLowerCase().contains(keyword.toLowerCase())) {
                        return false;
                    }
                }
                return true;

            default:
                // Par défaut, comparer directement
                return this.reponse_correct.contains(reponse);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
