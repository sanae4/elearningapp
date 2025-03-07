package com.example.elearning.service;
import com.example.elearning.model.User;
public interface UserService {
    User registerUser(User user); // Méthode pour enregistrer un utilisateur
    User findByEmail(String email); // Méthode pour trouver un utilisateur par email

}
