package com.example.elearning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private String statusCours;

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

}
