package com.example.elearning.controller;

import com.example.elearning.dto.QuizDTO;
import com.example.elearning.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizz")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        QuizDTO quiz = quizService.getQuizById(id);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesByCourseId(@PathVariable Long courseId) {
        List<QuizDTO> quizzes = quizService.getQuizzesByCourseId(courseId);
        return ResponseEntity.ok(quizzes);
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        QuizDTO createdQuiz = quizService.createQuiz(quizDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuiz);
    }


    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizDTO quizDTO) {
        QuizDTO updatedQuiz = quizService.updateQuiz(id, quizDTO);
        if (updatedQuiz != null) {
            return ResponseEntity.ok(updatedQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        boolean deleted = quizService.deleteQuiz(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}/hard")
    public ResponseEntity<Void> hardDeleteQuiz(@PathVariable Long id) {
        quizService.hardDeleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate/{courseId}")
    public ResponseEntity<QuizDTO> generateQuizForCourse(@PathVariable Long courseId) {
        QuizDTO generatedQuiz = quizService.generateQuizForCourse(courseId);
        if (generatedQuiz != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(generatedQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/{quizId}/correct")
    public ResponseEntity<QuizDTO> correctQuiz(@PathVariable Long quizId, @RequestBody List<String> reponses) {
        QuizDTO correctedQuiz = quizService.correctQuiz(quizId, reponses);
        if (correctedQuiz != null) {
            return ResponseEntity.ok(correctedQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Récupérer le quiz d'une leçon
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<QuizDTO> getQuizByLessonId(@PathVariable Long lessonId) {
        QuizDTO quiz = quizService.getQuizByLeconId(lessonId);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
