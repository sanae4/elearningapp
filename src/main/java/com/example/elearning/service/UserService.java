package com.example.elearning.service;
import com.example.elearning.model.Enseignant;
import com.example.elearning.model.Etudiant;
import com.example.elearning.model.User;
public interface UserService {
    User registerUser(User user); // Méthode pour enregistrer un utilisateur
    User findByEmail(String email); // Méthode pour trouver un utilisateur par email

    void deleteUser(Long id); // Méthode pour supprimer un utilisateur par son ID

        void saveEnseignant(Enseignant enseignant); // Enregistrer un enseignant
        void saveEtudiant(Etudiant etudiant); // Enregistrer un étudiant

    }

