package com.example.elearning.serviceImpl;

import com.example.elearning.model.User;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.service.AuthService;
import com.example.elearning.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String authenticate(String email, String password) {
        // Authentification de l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Charger les détails de l'utilisateur
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Récupérer l'ID de l'utilisateur depuis la base de données
        // Option 1: Si findByEmail retourne null quand l'utilisateur n'existe pas
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        // Option 2: Si vous préférez utiliser une vérification en une ligne
        // User user = Optional.ofNullable(userRepository.findByEmail(email))
        //     .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Générer le token avec l'ID utilisateur
        return jwtUtil.generateToken(userDetails, user.getId());
    }


    @Override
    public void registerUser(User user) {
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        userRepository.save(user);
    }
}
