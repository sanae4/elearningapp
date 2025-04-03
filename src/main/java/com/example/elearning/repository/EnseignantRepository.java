package com.example.elearning.repository;

import com.example.elearning.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
}