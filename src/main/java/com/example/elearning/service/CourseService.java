package com.example.elearning.service;

import com.example.elearning.dto.CourseCreateUpdateDTO;
import com.example.elearning.dto.CourseDTO;
import com.example.elearning.model.Course;

import java.util.List;

public interface CourseService {

    // Opérations CRUD de base
    List<CourseDTO> getAllCourses();
    CourseDTO getCourseById(Long id);
    CourseDTO createCourse(CourseCreateUpdateDTO courseDTO);
    CourseDTO updateCourse(Long id, CourseCreateUpdateDTO courseDTO);
    void deleteCourse(Long id);

    // Méthodes spécifiques
    List<CourseDTO> getCoursesByCategory(Long categoryId);
    List<CourseDTO> getCoursesByEnseignant(Long enseignantId);
    List<CourseDTO> searchCoursesByTitle(String title);
    List<CourseDTO> getCoursesByLevel(String level);
    List<CourseDTO> getCoursesByLanguage(String language);
     CourseDTO updateCourseStatus(Long id, String status) ;

    }
