package com.example.elearning.mapper;

import com.example.elearning.dto.ResultatDTO;
import com.example.elearning.model.Resultat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultatMapper {

    /**
     * Convertit une entité Resultat en DTO
     * @param resultat Entité Resultat
     * @return ResultatDTO
     */
    public ResultatDTO toDTO(Resultat resultat) {
        if (resultat == null) {
            return null;
        }

        ResultatDTO resultatDTO = new ResultatDTO();
        resultatDTO.setId(resultat.getId());
        resultatDTO.setScore(resultat.getScore());
        resultatDTO.setEstpasse(resultat.isEstpasse());

        if (resultat.getQuiz() != null) {
            resultatDTO.setQuizId(resultat.getQuiz().getId());
            resultatDTO.setQuizTitre(resultat.getQuiz().getTitre());
        }

        return resultatDTO;
    }

    /**
     * Convertit un ResultatDTO en entité Resultat
     * @param resultatDTO DTO Resultat
     * @return Entité Resultat
     */
    public Resultat toEntity(ResultatDTO resultatDTO) {
        if (resultatDTO == null) {
            return null;
        }

        Resultat resultat = new Resultat();
        resultat.setId(resultatDTO.getId());
        resultat.setScore(resultatDTO.getScore());
        resultat.setEstpasse(resultatDTO.isEstpasse());

        // La relation avec Quiz est généralement gérée par les services

        return resultat;
    }

    /**
     * Convertit une liste d'entités Resultat en liste de DTOs
     * @param resultats Liste d'entités Resultat
     * @return Liste de ResultatDTOs
     */
    public List<ResultatDTO> toDTOList(List<Resultat> resultats) {
        return resultats.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
