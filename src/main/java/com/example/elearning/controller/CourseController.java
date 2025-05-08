package com.example.elearning.controller;

import com.example.elearning.dto.CourseCreateUpdateDTO;
import com.example.elearning.dto.CourseDTO;
import com.example.elearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseCreateUpdateDTO courseDTO) {
        return new ResponseEntity<>(courseService.createCourse(courseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseCreateUpdateDTO courseDTO) {
        return ResponseEntity.ok(courseService.updateCourse(id, courseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints spécifiques pour la relation avec Category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(courseService.getCoursesByCategory(categoryId));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByEnseignant(@PathVariable Long enseignantId) {
        return ResponseEntity.ok(courseService.getCoursesByEnseignant(enseignantId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCoursesByTitle(@RequestParam String title) {
        return ResponseEntity.ok(courseService.searchCoursesByTitle(title));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<CourseDTO>> getCoursesByLevel(@PathVariable String level) {
        return ResponseEntity.ok(courseService.getCoursesByLevel(level));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<CourseDTO>> getCoursesByLanguage(@PathVariable String language) {
        return ResponseEntity.ok(courseService.getCoursesByLanguage(language));
    }
    // In CourseController.java
    @PutMapping("/{id}/status")
    public ResponseEntity<CourseDTO> updateCourseStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate
    ) {
        return ResponseEntity.ok(
                courseService.updateCourseStatus(id, statusUpdate.get("status"))
        );
    }
    @PutMapping("/{id}/publish")
    public ResponseEntity<CourseDTO> publishCourse(@PathVariable Long id) {
        CourseDTO updatedCourse = courseService.updateCourseStatus(id, "PUBLISHED");
        return ResponseEntity.ok(updatedCourse);
    }
}
