package com.example.elearning.service;

import com.example.elearning.dto.ChapitreDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ChapitreService {

    Optional<ChapitreDTO> getChapitreById(Long id);
    ChapitreDTO createChapitre(ChapitreDTO chapitreDTO);
    ChapitreDTO updateChapitre(Long id, ChapitreDTO chapitreDTO);
    void deleteChapitre(Long id);

    List<ChapitreDTO> getChapitresByLeconId(Long leconId);


}