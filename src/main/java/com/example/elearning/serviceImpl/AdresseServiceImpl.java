package com.example.elearning.serviceImpl;

import com.example.elearning.model.Adresse;
import com.example.elearning.repository.AdresseRepository;
import com.example.elearning.service.AdresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseServiceImpl implements AdresseService {

    @Autowired
    private AdresseRepository adresseRepository;

    @Override
    public Adresse saveAdresse(Adresse adresse) {
        return adresseRepository.save(adresse);
    }

    @Override
    public Adresse getAdresseById(Long id) {
        return adresseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Adresse non trouvée avec l'ID : " + id));
    }

    @Override
    public List<Adresse> getAllAdresses() {
        return adresseRepository.findAll();
    }

    @Override
    public Adresse updateAdresse(Long id, Adresse adresse) {
        Adresse existingAdresse = getAdresseById(id);
        existingAdresse.setRue(adresse.getRue());
        existingAdresse.setVille(adresse.getVille());
        existingAdresse.setCodePostal(adresse.getCodePostal());
        existingAdresse.setPays(adresse.getPays());
        return adresseRepository.save(existingAdresse);
    }

    @Override
    public void deleteAdresse(Long id) {
        if (!adresseRepository.existsById(id)) {
            throw new IllegalArgumentException("Adresse non trouvée avec l'ID : " + id);
        }
        adresseRepository.deleteById(id);
    }
}