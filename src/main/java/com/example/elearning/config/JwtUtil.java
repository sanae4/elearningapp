package com.example.elearning.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    // Clé secrète pour signer le token JWT
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Utilisation de HS512
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Générer un token JWT avec le rôle de l'utilisateur
// Générer un token JWT avec le rôle et l'ID de l'utilisateur
    public String generateToken(UserDetails userDetails, Long userId) {
        Map<String, Object> claims = new HashMap<>();

        // Ajouter le rôle de l'utilisateur dans les claims
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("role", role);

        // Ajouter l'ID de l'utilisateur dans les claims
        claims.put("id", userId);

        return createToken(claims, userDetails.getUsername());
    }


    // Créer un token JWT avec les claims et le sujet (sujet = email ou username)
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Ajouter les claims (ici, le rôle)
                .setSubject(subject) // Sujet du token (email ou username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Date d'expiration
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Signature avec la clé secrète
                .compact(); // Générer le token
    }

    // Valider le token JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Extraire le sujet (email ou username) du token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire la date d'expiration du token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraire une revendication spécifique du token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire toutes les revendications du token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Clé secrète pour vérifier la signature
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Vérifier si le token est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraire le rôle du token
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
}