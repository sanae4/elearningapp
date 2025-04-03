package com.example.elearning.controller;

import com.example.elearning.dto.EnseignantDTO;
import com.example.elearning.model.Adresse;
import com.example.elearning.model.Enseignant;
import com.example.elearning.model.Etudiant;
import com.example.elearning.model.User;
import com.example.elearning.repository.AdresseRepository;
import com.example.elearning.repository.EnseignantRepository;
import com.example.elearning.repository.UserRepository;
import com.example.elearning.service.AuthService;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.elearning.serviceImpl.EmailVerificationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/api/users")
public class registerController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private UserService userService;
@Autowired
private EnseignantRepository enseignantRepository;
    @PostMapping("/send-verification-code")
    public ResponseEntity<String> sendVerificationCode(@RequestParam String email) {
        // Vérifier si l'email est vide
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("L'email ne peut pas être vide");
        }

        // Vérifier si l'email est valide
        if (!isValidEmail(email)) {
            return ResponseEntity.badRequest().body("L'email est invalide");
        }

        // Vérifier si l'email existe déjà
        if (emailExists(email)) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }

        // Générer et envoyer le code de vérification
        String code = emailVerificationService.generateVerificationCode();
        emailVerificationService.storeVerificationCode(email, code);
        emailVerificationService.sendVerificationEmail(email, code);

        return ResponseEntity.ok("Code de vérification envoyé à " + email);
    }

    // Méthode pour valider l'email avec une regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Méthode pour vérifier si l'email existe déjà dans la base de données
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam String code,
            @RequestBody Map<String, Object> userData, // Utiliser une Map pour capturer les données dynamiques
            @RequestParam String role // Ajouter un paramètre pour spécifier le rôle
    ) {
        // Vérifier si l'email est déjà utilisé
        String email = (String) userData.get("email");
        if (userRepository.findByEmail(email) != null) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }

        // Vérifier le code de vérification
        if (!emailVerificationService.verifyCode(email, code)) {
            return ResponseEntity.badRequest().body("Code de vérification invalide ou expiré");
        }

        // Hacher le mot de passe avant de sauvegarder l'utilisateur
        String hashedPassword = passwordEncoder.encode((String) userData.get("motDePasse"));
        userData.put("motDePasse", hashedPassword);

        // Enregistrer l'utilisateur en fonction du rôle
        switch (role.toUpperCase()) {
            case "ETUDIANT":
                // Créer un nouvel étudiant
                Etudiant etudiant = new Etudiant();
                // Copier les attributs de User vers Etudiant
                copyUserAttributes(userData, etudiant);
                etudiant.setRole("ETUDIANT");
                // Initialiser les attributs spécifiques à l'étudiant
                etudiant.setCertificat((String) userData.get("certificat"));
                etudiant.setLangue((String) userData.get("langue"));
                userService.saveEtudiant(etudiant);
                return ResponseEntity.ok("Étudiant enregistré avec succès !");

            case "ENSEIGNANT":
                // Créer un nouvel enseignant
                Enseignant enseignant = new Enseignant();
                // Copier les attributs de User vers Enseignant
                copyUserAttributes(userData, enseignant);
                enseignant.setRole("ENSEIGNANT");
                // Initialiser les attributs spécifiques à l'enseignant
                enseignant.setBiographie((String) userData.get("biographie"));
                enseignant.setStatus(false); // Définir le statut à false par défaut
                enseignant.setSpecialite((String) userData.get("specialite"));
                enseignant.setAnneesExperience((String) userData.get("anneesExperience"));
                userService.saveEnseignant(enseignant);
                return ResponseEntity.ok("Enseignant enregistré avec succès !");
            case "ADMIN":
                // Créer un nouvel admin (si nécessaire)
                User admin = new User();
                copyUserAttributes(userData, admin);
                admin.setRole("ADMIN");
                userRepository.save(admin);
                return ResponseEntity.ok("Admin enregistré avec succès !");

            default:
                return ResponseEntity.badRequest().body("Rôle non reconnu");
        }
    }

    // Méthode utilitaire pour copier les attributs de User vers une sous-classe
    private void copyUserAttributes(Map<String, Object> source, User target) {
        target.setNom((String) source.get("nom"));
        target.setPrenom((String) source.get("prenom"));
        target.setNumtele((String) source.get("numtele"));
        target.setEmail((String) source.get("email"));
        target.setMotDePasse((String) source.get("motDePasse"));
        // Convertir la chaîne en Date
        String dateNaissanceStr = (String) source.get("datenaissance");
        if (dateNaissanceStr != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNaissance = dateFormat.parse(dateNaissanceStr);
                target.setDatenaissance(dateNaissance);
            } catch (ParseException e) {
                throw new RuntimeException("Format de date invalide. Utilisez le format yyyy-MM-dd.", e);
            }
        }
        target.setGenre((String) source.get("genre"));
        // Copier l'adresse
        Map<String, Object> adresseData = (Map<String, Object>) source.get("adresse");
        if (adresseData != null) {
            Adresse adresse = new Adresse();
            adresse.setRue((String) adresseData.get("rue"));
            adresse.setVille((String) adresseData.get("ville"));
            adresse.setPays((String) adresseData.get("pays"));
            target.setAdresse(adresse);
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
    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
