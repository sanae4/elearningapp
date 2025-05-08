package com.example.elearning.service.impl;

import com.example.elearning.dto.QuestionDTO;
import com.example.elearning.mapper.QuestionMapper;
import com.example.elearning.model.Question;
import com.example.elearning.model.Quiz;
import com.example.elearning.repository.QuestionRepository;
import com.example.elearning.repository.QuizRepository;
import com.example.elearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.questionMapper = questionMapper;
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questionMapper.toDTOList(questions);
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        return questionOptional.map(questionMapper::toDTO).orElse(null);
    }

    @Override
    public List<QuestionDTO> getQuestionsByQuizId(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);
        return questionMapper.toDTOList(questions);
    }

    @Override
    public List<QuestionDTO> getQuestionsByType(String type) {
        List<Question> questions = questionRepository.findByType(type);
        return questionMapper.toDTOList(questions);
    }

    @Override
    @Transactional
    public QuestionDTO createQuestion(QuestionDTO questionDTO) {
        Question question = questionMapper.toEntity(questionDTO);

        // Associer la question au quiz si quizId est fourni
        if (questionDTO.getQuizId() != null) {
            Optional<Quiz> quizOptional = quizRepository.findById(questionDTO.getQuizId());
            quizOptional.ifPresent(question::setQuiz);
        }

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDTO(savedQuestion);
    }

    @Override
    @Transactional
    public QuestionDTO updateQuestion(Long id, QuestionDTO questionDTO) {
        Optional<Question> questionOptional = questionRepository.findById(id);

        if (questionOptional.isPresent()) {
            Question existingQuestion = questionOptional.get();

            // Mettre à jour les propriétés
            existingQuestion.setTexte(questionDTO.getTexte());
            existingQuestion.setType(questionDTO.getType());
            existingQuestion.setOptions(questionDTO.getOptions());
            existingQuestion.setReponse_correct(questionDTO.getReponse_correct());

            // Mettre à jour la relation avec le quiz si quizId est fourni
            if (questionDTO.getQuizId() != null) {
                Optional<Quiz> quizOptional = quizRepository.findById(questionDTO.getQuizId());
                quizOptional.ifPresent(existingQuestion::setQuiz);
            }

            Question updatedQuestion = questionRepository.save(existingQuestion);
            return questionMapper.toDTO(updatedQuestion);
        }

        return null;
    }

    @Override
    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public boolean verifierReponse(Long questionId, String reponse) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);

        if (questionOptional.isPresent()) {
            Question question = questionOptional.get();
            return question.verifierReponse(reponse);
        }

        return false;
    }
}
