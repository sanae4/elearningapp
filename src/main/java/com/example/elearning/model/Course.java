package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String about;
    private String courselevel;
    private String titreCours;
    private String description;
    private double prix;
    private Date datePublication;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;
    private String langage;

    private String statusCours; //(APPROVED,PUBLISHED,DRAFT)

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    @JsonBackReference("enseignant-courses") // Évite la sérialisation circulaire
    private Enseignant enseignant;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference("etudiant-courses") // Ignore la sérialisation de cette collection
    private Set<Etudiant> etudiants;

    // Ajout de la relation avec Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference("category-courses")
    private Category category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leçon> leçons;

    // Dans la classe Course, changez de @OneToMany à @OneToOne
    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference("course-quiz")
    private Quiz quiz;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * Ajoute un quiz au cours
     * @param quiz Quiz à ajouter
     */
    public void ajouterQuiz(Quiz quiz) {
        quiz.setCourse(this);
        this.quiz = quiz;
    }

    /**
     * Supprime le quiz du cours
     */
    public void supprimerQuiz() {
        if (this.quiz != null) {
            this.quiz.setCourse(null);
            this.quiz = null;
        }
    }

    /**
     * Génère automatiquement un quiz basé sur le contenu du cours
     * @return Quiz généré
     */
    public Quiz genererQuizAutomatique() {
        Quiz quiz = new Quiz();
        quiz.setCourse(this);
        quiz.setTitre("Quiz automatique - " + this.titreCours);
        quiz.setEstsupprimer(false);
        quiz.setQuizType("COURSE");

        // Appel à la méthode de génération automatique du quiz
        quiz.genererQuiz();

        this.quiz = quiz;
        return quiz;
    }
}
