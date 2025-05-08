package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Setter
@Getter
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private boolean estsupprimer;
    // Type de quiz: "truefalse","generatif","multiple choix" (peut etre 2 ou 3 a la fois selon le choix de user )
    private String quizType;


    // Relation avec Lecon (une leçon peut avoir un quiz)
    @OneToOne
    @JoinColumn(name = "lecon_id")
    @JsonBackReference("lecon-quiz")
    private Leçon lecon;

    // Relation avec Course (un cours peut avoir un quiz)
    @OneToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference("course-quiz")
    private Course course;


    // Relation avec Question (un quiz a plusieurs questions)
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("quiz-questions")
    private List<Question> questions = new ArrayList<>();

    // Relation avec Résultat (un quiz a un résultat)
    @OneToOne(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("quiz-resultat")
    private Resultat resultat;

    // Méthodes demandées dans le diagramme UML

    /**
     * Génère un quiz automatiquement basé sur le contenu du cours
     * @return Quiz généré
     */
    public Quiz genererQuiz() {
        // Logique pour générer un quiz automatiquement
        // Cette méthode pourrait utiliser des services externes ou des algorithmes
        // pour générer des questions basées sur le contenu du cours

        // Exemple simple de création d'un quiz avec une question
        this.titre = "Quiz généré automatiquement pour " + this.course.getTitreCours();

        Question question = new Question();
        question.setTexte("Question générée automatiquement");
        question.setType("QCM");
        question.setOptions("Option 1, Option 2, Option 3");
        question.setReponse_correct("{\"correct\": \"Option 1\"}");
        question.setQuiz(this);

        this.questions.add(question);

        // Création d'un résultat vide
        Resultat resultat = new Resultat();
        resultat.setScore(0);
        resultat.setEstpasse(false);
        resultat.setQuiz(this);
        this.resultat = resultat;

        return this;
    }

    /**
     * Crée un quiz manuellement avec les questions fournies
     * @param titre Titre du quiz
     * @param questions Liste des questions
     * @return Quiz créé
     */
    public Quiz créerQuizmanuel(String titre, List<Question> questions) {
        this.titre = titre;

        // Ajouter chaque question au quiz
        for (Question question : questions) {
            question.setQuiz(this);
            this.questions.add(question);
        }

        // Création d'un résultat vide
        Resultat resultat = new Resultat();
        resultat.setScore(0);
        resultat.setEstpasse(false);
        resultat.setQuiz(this);
        this.resultat = resultat;

        return this;
    }

    /**
     * Corrige le quiz en fonction des réponses fournies
     * @param reponses Liste des réponses aux questions
     * @return Résultat du quiz
     */
    public Resultat corrigerQuiz(List<String> reponses) {
        if (reponses.size() != questions.size()) {
            throw new IllegalArgumentException("Le nombre de réponses ne correspond pas au nombre de questions");
        }

        int score = 0;

        // Vérifier chaque réponse
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String reponseUtilisateur = reponses.get(i);

            // Logique de vérification selon le type de question
            // Cette logique devrait être adaptée selon la structure JSON utilisée
            if (question.getReponse_correct().contains(reponseUtilisateur)) {
                score++;
            }
        }

        // Calculer le score en pourcentage
        double scorePercentage = (double) score / questions.size() * 100;

        // Mettre à jour le résultat
        if (this.resultat == null) {
            this.resultat = new Resultat();
            this.resultat.setQuiz(this);
        }

        this.resultat.setScore((int) scorePercentage);
        this.resultat.setEstpasse(scorePercentage >= 50); // Considérer comme réussi si score >= 50%

        return this.resultat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return Objects.equals(id, quiz.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
