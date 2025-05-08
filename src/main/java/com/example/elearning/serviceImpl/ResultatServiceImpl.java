package com.example.elearning.serviceImpl;

import com.example.elearning.dto.ResultatDTO;
import com.example.elearning.mapper.ResultatMapper;
import com.example.elearning.model.Quiz;
import com.example.elearning.model.Resultat;
import com.example.elearning.repository.QuizRepository;
import com.example.elearning.repository.ResultatRepository;
import com.example.elearning.service.ResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ResultatServiceImpl implements ResultatService {

    private final ResultatRepository resultatRepository;
    private final QuizRepository quizRepository;
    private final ResultatMapper resultatMapper;

    @Autowired
    public ResultatServiceImpl(ResultatRepository resultatRepository, QuizRepository quizRepository, ResultatMapper resultatMapper) {
        this.resultatRepository = resultatRepository;
        this.quizRepository = quizRepository;
        this.resultatMapper = resultatMapper;
    }

    @Override
    public List<ResultatDTO> getAllResultats() {
        List<Resultat> resultats = resultatRepository.findAll();
        return resultatMapper.toDTOList(resultats);
    }

    @Override
    public ResultatDTO getResultatById(Long id) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(id);
        return resultatOptional.map(resultatMapper::toDTO).orElse(null);
    }

    @Override
    public ResultatDTO getResultatByQuizId(Long quizId) {
        Optional<Resultat> resultatOptional = resultatRepository.findByQuizId(quizId);
        return resultatOptional.map(resultatMapper::toDTO).orElse(null);
    }

    @Override
    public List<ResultatDTO> getResultatsReussis() {
        List<Resultat> resultats = resultatRepository.findByestpasseTrue();
        return resultatMapper.toDTOList(resultats);
    }

    @Override
    public List<ResultatDTO> getResultatsEchoues() {
        List<Resultat> resultats = resultatRepository.findByestpasseFalse();
        return resultatMapper.toDTOList(resultats);
    }

    @Override
    @Transactional
    public ResultatDTO createResultat(ResultatDTO resultatDTO) {
        Resultat resultat = resultatMapper.toEntity(resultatDTO);

        // Associer le résultat au quiz si quizId est fourni
        if (resultatDTO.getQuizId() != null) {
            Optional<Quiz> quizOptional = quizRepository.findById(resultatDTO.getQuizId());
            quizOptional.ifPresent(resultat::setQuiz);
        }

        Resultat savedResultat = resultatRepository.save(resultat);
        return resultatMapper.toDTO(savedResultat);
    }

    @Override
    @Transactional
    public ResultatDTO updateResultat(Long id, ResultatDTO resultatDTO) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(id);

        if (resultatOptional.isPresent()) {
            Resultat existingResultat = resultatOptional.get();

            // Mettre à jour les propriétés
            existingResultat.setScore(resultatDTO.getScore());
            existingResultat.setEstpasse(resultatDTO.isEstpasse());

            // Mettre à jour la relation avec le quiz si quizId est fourni
            if (resultatDTO.getQuizId() != null) {
                Optional<Quiz> quizOptional = quizRepository.findById(resultatDTO.getQuizId());
                quizOptional.ifPresent(existingResultat::setQuiz);
            }

            Resultat updatedResultat = resultatRepository.save(existingResultat);
            return resultatMapper.toDTO(updatedResultat);
        }

        return null;
    }

    @Override
    @Transactional
    public void deleteResultat(Long id) {
        resultatRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean calculerReussite(Long resultatId, int scoreMinimum) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(resultatId);

        if (resultatOptional.isPresent()) {
            Resultat resultat = resultatOptional.get();
            boolean reussite = resultat.calculerReussite(scoreMinimum);
            resultatRepository.save(resultat);
            return reussite;
        }

        return false;
    }

    @Override
    public String genererRapport(Long resultatId) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(resultatId);

        if (resultatOptional.isPresent()) {
            Resultat resultat = resultatOptional.get();
            return resultat.genererRapport();
        }

        return "Résultat non trouvé";
    }

    @Override
    public Double calculerScoreMoyen(Long quizId) {
        return resultatRepository.calculateAverageScoreByQuizId(quizId);
    }
}
