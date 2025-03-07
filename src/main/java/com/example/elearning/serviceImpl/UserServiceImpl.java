package com.example.elearning.serviceImpl;

import com.example.elearning.model.User;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerUser(User user) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Hasher le mot de passe avant de sauvegarder (optionnel)
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email); // Retourne un User ou null
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé avec l'email : " + email);
        }
        return user;
    }
}
