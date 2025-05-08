package com.example.elearning.mapper;

import com.example.elearning.dto.QuestionDTO;
import com.example.elearning.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    /**
     * Convertit une entité Question en DTO
     * @param question Entité Question
     * @return QuestionDTO
     */
    public QuestionDTO toDTO(Question question) {
        if (question == null) {
            return null;
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setTexte(question.getTexte());
        questionDTO.setType(question.getType());
        questionDTO.setOptions(question.getOptions());
        questionDTO.setReponse_correct(question.getReponse_correct());

        if (question.getQuiz() != null) {
            questionDTO.setQuizId(question.getQuiz().getId());
        }

        return questionDTO;
    }

    /**
     * Convertit un QuestionDTO en entité Question
     * @param questionDTO DTO Question
     * @return Entité Question
     */
    public Question toEntity(QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return null;
        }

        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTexte(questionDTO.getTexte());
        question.setType(questionDTO.getType());
        question.setOptions(questionDTO.getOptions());
        question.setReponse_correct(questionDTO.getReponse_correct());

        // La relation avec Quiz est généralement gérée par les services

        return question;
    }

    /**
     * Convertit une liste d'entités Question en liste de DTOs
     * @param questions Liste d'entités Question
     * @return Liste de QuestionDTOs
     */
    public List<QuestionDTO> toDTOList(List<Question> questions) {
        return questions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
