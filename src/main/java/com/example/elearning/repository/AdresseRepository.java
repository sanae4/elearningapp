package com.example.elearning.repository;

import com.example.elearning.model.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse, Long> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}