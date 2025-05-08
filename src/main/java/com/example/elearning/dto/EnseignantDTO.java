package com.example.elearning.dto;

import com.example.elearning.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class EnseignantDTO extends User {

        private Long id;

        // Propriétés de User
        private String nom;
        private String prenom;
        private String numtele;
        private String email;
        private String motDePasse;
        private String role;
        private Date datenaissance;
        private String genre;

        // Propriétés spécifiques à Enseignant
        private String biographie;
        private boolean status;
        private String specialite;
        private String anneesExperience;

        // Pas de liste de cours pour éviter les problèmes de sérialisation
    }


