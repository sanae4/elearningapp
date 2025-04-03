package com.example.elearning.service;

import com.example.elearning.model.Adresse;
import java.util.List;

public interface AdresseService {
    Adresse saveAdresse(Adresse adresse);
    Adresse getAdresseById(Long id);
    List<Adresse> getAllAdresses();
    Adresse updateAdresse(Long id, Adresse adresse);
    void deleteAdresse(Long id);
}