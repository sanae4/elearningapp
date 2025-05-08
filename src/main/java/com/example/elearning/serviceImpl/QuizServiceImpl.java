package com.example.elearning.serviceImpl;

import com.example.elearning.dto.QuestionDTO;
import com.example.elearning.dto.QuizDTO;
import com.example.elearning.mapper.QuizMapper;
import com.example.elearning.model.*;
import com.example.elearning.repository.CourseRepository;
import com.example.elearning.repository.LeçonRepository;
import com.example.elearning.repository.QuestionRepository;
import com.example.elearning.repository.QuizRepository;
import com.example.elearning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final CourseRepository courseRepository;
    private final QuizMapper quizMapper;

    private final QuestionRepository questionRepository;


    @Autowired
    private LeçonRepository leconRepository;
    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, CourseRepository courseRepository, QuizMapper quizMapper, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.courseRepository = courseRepository;
        this.quizMapper = quizMapper;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizMapper.toDTOList(quizzes);
    }

    @Override
    public QuizDTO getQuizById(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        return quizOptional.map(quizMapper::toDTO).orElse(null);
    }

    @Override
    public List<QuizDTO> getQuizzesByCourseId(Long courseId) {
        List<Quiz> quizzes = quizRepository.findByCourseId(courseId);
        return quizMapper.toDTOList(quizzes);
    }
    @Override
    @Transactional
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        // 1. D'abord, créer le quiz sans les questions
        Quiz quiz = new Quiz();
        quiz.setTitre(quizDTO.getTitre());
        quiz.setQuizType(quizDTO.getQuizType());
        quiz.setEstsupprimer(quizDTO.isEstsupprimer());

        // 2. Définir les relations avec cours et leçon
        if (quizDTO.getCourseId() != null) {
            Optional<Course> course = courseRepository.findById(quizDTO.getCourseId());
            if (course.isPresent()) {
                quiz.setCourse(course.get());
            }
        }

        if (quizDTO.getLeconId() != null) {
            Optional<Leçon> lecon = leconRepository.findById(quizDTO.getLeconId());
            if (lecon.isPresent()) {
                quiz.setLecon(lecon.get());
            }
        }

        // 3. Sauvegarder le quiz pour obtenir son ID
        Quiz savedQuiz = quizRepository.save(quiz);

        // 4. Créer et associer les questions
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            List<Question> questions = new ArrayList<>();

            for (QuestionDTO questionDTO : quizDTO.getQuestions()) {
                Question question = new Question();
                question.setTexte(questionDTO.getTexte());
                question.setType(questionDTO.getType());
                question.setOptions(questionDTO.getOptions());
                question.setReponse_correct(questionDTO.getReponse_correct());

                // Point critique : définir explicitement la relation avec le quiz
                question.setQuiz(savedQuiz);

                questions.add(question);
            }

            // 5. Sauvegarder toutes les questions
            List<Question> savedQuestions = questionRepository.saveAll(questions);

            // 6. Mettre à jour la liste des questions du quiz
            savedQuiz.setQuestions(savedQuestions);
        }

        // 7. Retourner le DTO avec toutes les informations mises à jour
        return convertToDTO(savedQuiz);
    }



    @Override
    @Transactional
    public QuizDTO updateQuiz(Long id, QuizDTO quizDTO) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isPresent()) {
            Quiz existingQuiz = quizOptional.get();

            // Update properties
            existingQuiz.setTitre(quizDTO.getTitre());
            existingQuiz.setEstsupprimer(quizDTO.isEstsupprimer());
            existingQuiz.setQuizType(quizDTO.getQuizType());  // Add this line

            // Update course relationship
            if (quizDTO.getCourseId() != null) {
                Optional<Course> courseOptional = courseRepository.findById(quizDTO.getCourseId());
                courseOptional.ifPresent(existingQuiz::setCourse);
            }

            // Update lesson relationship - Add this block
            if (quizDTO.getLeconId() != null) {
                Optional<Leçon> leconOptional = leconRepository.findById(quizDTO.getLeconId());
                leconOptional.ifPresent(existingQuiz::setLecon);
            }

            Quiz updatedQuiz = quizRepository.save(existingQuiz);
            return quizMapper.toDTO(updatedQuiz);
        }

        return null;
    }


    @Override
    @Transactional
    public boolean deleteQuiz(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);

        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setEstsupprimer(true);
            quizRepository.save(quiz);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public void hardDeleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    @Transactional
    public QuizDTO generateQuizForCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            // Utiliser la méthode genererQuizAutomatique de Course
            Quiz generatedQuiz = course.genererQuizAutomatique();

            // Sauvegarder le quiz généré
            Quiz savedQuiz = quizRepository.save(generatedQuiz);

            return quizMapper.toDTO(savedQuiz);
        }

        return null;
    }

    @Override
    @Transactional
    public QuizDTO correctQuiz(Long quizId, List<String> reponses) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);

        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();

            // Utiliser la méthode corrigerQuiz de Quiz
            Resultat resultat = quiz.corrigerQuiz(reponses);

            // Sauvegarder le quiz avec le résultat mis à jour
            Quiz updatedQuiz = quizRepository.save(quiz);

            return quizMapper.toDTO(updatedQuiz);
        }

        return null;
    }


    // Récupérer le quiz d'une leçon spécifique
    public QuizDTO getQuizByLeconId(Long leconId) {
        Leçon lecon = leconRepository.findById(leconId)
                .orElseThrow(() -> new RuntimeException("Lecon not found"));
        if (lecon.getQuiz() != null) {
            return convertToDTO(lecon.getQuiz());
        }
        return null;
    }

    // Méthode de conversion pour les DTO
    private QuizDTO convertToDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setTitre(quiz.getTitre());
        dto.setQuizType(quiz.getQuizType());
        dto.setEstsupprimer(quiz.isEstsupprimer());

        if (quiz.getCourse() != null) {
            dto.setCourseId(quiz.getCourse().getId());
        }

        if (quiz.getLecon() != null) {
            dto.setLeconId(quiz.getLecon().getId());
        }

        // Convertir toutes les questions associées
        if (quiz.getQuestions() != null) {
            List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                    .map(q -> {
                        QuestionDTO qdto = new QuestionDTO();
                        qdto.setId(q.getId());
                        qdto.setTexte(q.getTexte());
                        qdto.setType(q.getType());
                        qdto.setOptions(q.getOptions());
                        qdto.setReponse_correct(q.getReponse_correct());
                        qdto.setQuizId(quiz.getId());  // Conserver la référence au quiz
                        return qdto;
                    })
                    .collect(Collectors.toList());
            dto.setQuestions(questionDTOs);
        }

        if (quiz.getResultat() != null) {
            dto.setResultatId(quiz.getResultat().getId());
        }
        return dto;
    }


}
