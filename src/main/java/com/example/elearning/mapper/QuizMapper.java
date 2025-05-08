package com.example.elearning.mapper;

import com.example.elearning.dto.QuestionDTO;
import com.example.elearning.dto.QuizDTO;
import com.example.elearning.model.Question;
import com.example.elearning.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;
    private final ResultatMapper resultatMapper;

    public QuizMapper(QuestionMapper questionMapper, ResultatMapper resultatMapper) {
        this.questionMapper = questionMapper;
        this.resultatMapper = resultatMapper;
    }


    public QuizDTO toDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setId(quiz.getId());
        quizDTO.setTitre(quiz.getTitre());
        quizDTO.setEstsupprimer(quiz.isEstsupprimer());

        if (quiz.getCourse() != null) {
            quizDTO.setCourseId(quiz.getCourse().getId());

        }

        if (quiz.getQuestions() != null) {
            quizDTO.setQuestions(quiz.getQuestions().stream()
                    .map(questionMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        if (quiz.getResultat() != null) {
            quizDTO.setResultat(resultatMapper.toDTO(quiz.getResultat()));
        }

        return quizDTO;
    }



    public Quiz toEntity(QuizDTO dto) {
        Quiz quiz = new Quiz();
        quiz.setTitre(dto.getTitre());
        quiz.setEstsupprimer(dto.isEstsupprimer());

        if (dto.getQuestions() != null) {
            List<Question> questions = dto.getQuestions().stream()
                    .map(this::toQuestionEntity)
                    .collect(Collectors.toList());
            quiz.setQuestions(questions);
        }

        return quiz;
    }

    private Question toQuestionEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setTexte(dto.getTexte());
        question.setType(dto.getType());
        question.setOptions(dto.getOptions());
        question.setReponse_correct(dto.getReponse_correct());
        return question;
    }
    public List<QuizDTO> toDTOList(List<Quiz> quizzes) {
        return quizzes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
