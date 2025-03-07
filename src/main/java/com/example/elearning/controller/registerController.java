package com.example.elearning.controller;

import com.example.elearning.model.User;
import com.example.elearning.service.AuthService;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.elearning.serviceImpl.EmailVerificationService;


@RestController
@RequestMapping("/api/users")
public class registerController {


    @Autowired
    private AuthService authService;

    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private UserService userService;

    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        String code = emailVerificationService.generateVerificationCode();
        emailVerificationService.storeVerificationCode(email, code);
        emailVerificationService.sendVerificationEmail(email, code);

        return ResponseEntity.ok("Code de vérification envoyé à " + email);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user, @RequestParam String code) {
        if (emailVerificationService.verifyCode(user.getEmail(), code)) {
            authService.registerUser(user);
            return ResponseEntity.ok("Inscription réussie");
        } else {
            return ResponseEntity.badRequest().body("Code de vérification invalide");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.findByEmail(email); // Appel de la méthode
            return ResponseEntity.ok("Utilisateur trouvé : " + user.getNom());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Gestion de l'exception
        }
    }

}
