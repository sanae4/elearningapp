package com.example.elearning.repository;

import com.example.elearning.model.Chapitre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapitreRepository extends JpaRepository<Chapitre, Long> {
    List<Chapitre> findByLeconId(Long leconId);
}