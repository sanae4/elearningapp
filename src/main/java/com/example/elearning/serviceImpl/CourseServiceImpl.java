package com.example.elearning.serviceImpl;

import com.example.elearning.dto.CategoryDTO;
import com.example.elearning.dto.CourseCreateUpdateDTO;
import com.example.elearning.dto.CourseDTO;
import com.example.elearning.dto.EnseignantDTO;
import com.example.elearning.model.Category;
import com.example.elearning.model.Course;
import com.example.elearning.model.Enseignant;
import com.example.elearning.repository.CategoryRepository;
import com.example.elearning.repository.CourseRepository;
import com.example.elearning.repository.EnseignantRepository;
import com.example.elearning.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final EnseignantRepository enseignantRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             CategoryRepository categoryRepository,
                             EnseignantRepository enseignantRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.enseignantRepository = enseignantRepository;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'ID: " + id));
        return convertToDTO(course);
    }

    @Override
    @Transactional
    public CourseDTO createCourse(CourseCreateUpdateDTO courseDTO) {
        Course course = new Course();
        updateCourseFromDTO(course, courseDTO);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(Long id, CourseCreateUpdateDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'ID: " + id));

        updateCourseFromDTO(course, courseDTO);
        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Cours non trouvé avec l'ID: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDTO> getCoursesByCategory(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByEnseignant(Long enseignantId) {
        return courseRepository.findByEnseignantId(enseignantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> searchCoursesByTitle(String title) {
        return courseRepository.findByTitreCoursContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // In CourseService.java
    public CourseDTO updateCourseStatus(Long id, String status) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.setStatusCours(status);
        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    public List<CourseDTO> getCoursesByLevel(String level) {
        return courseRepository.findByCourselevel(level).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesByLanguage(String language) {
        return courseRepository.findByLangage(language).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Méthodes utilitaires
    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setAbout(course.getAbout());
        dto.setCourselevel(course.getCourselevel());
        dto.setTitreCours(course.getTitreCours());
        dto.setDescription(course.getDescription());
        dto.setPrix(course.getPrix());
        dto.setDatePublication(course.getDatePublication());
        dto.setImage(course.getImage());
        dto.setLangage(course.getLangage());
        dto.setStatusCours(course.getStatusCours());

        // Gestion de l'enseignant
        if (course.getEnseignant() != null) {
            // Conserver ces champs pour la rétrocompatibilité
            dto.setEnseignantId(course.getEnseignant().getId());
            dto.setEnseignantNom(course.getEnseignant().getNom());

            // Créer l'objet EnseignantDTO complet
            EnseignantDTO enseignantDTO = new EnseignantDTO();
            enseignantDTO.setId(course.getEnseignant().getId());
            enseignantDTO.setNom(course.getEnseignant().getNom());
            enseignantDTO.setPrenom(course.getEnseignant().getPrenom());
            enseignantDTO.setBiographie(course.getEnseignant().getBiographie());
            enseignantDTO.setStatus(course.getEnseignant().isStatus());
            enseignantDTO.setSpecialite(course.getEnseignant().getSpecialite());
            enseignantDTO.setAnneesExperience(course.getEnseignant().getAnneesExperience());

            dto.setEnseignant(enseignantDTO);
        }

        // Gestion de la catégorie
        if (course.getCategory() != null) {
            // Conserver ce champ pour la rétrocompatibilité
            dto.setCategoryId(course.getCategory().getId());

            // Créer l'objet CategoryDTO
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(course.getCategory().getId());
            categoryDTO.setTitre(course.getCategory().getTitre());
            categoryDTO.setDescription(course.getCategory().getDescription());
            categoryDTO.setIcon(course.getCategory().getIcon());

            if (course.getCategory().getParentCategory() != null) {
                categoryDTO.setParentCategoryId(course.getCategory().getParentCategory().getId());
            }

            dto.setCategory(categoryDTO);
        }

        // Gestion des étudiants
        if (course.getEtudiants() != null && !course.getEtudiants().isEmpty()) {
            dto.setEtudiantIds(course.getEtudiants().stream()
                    .map(etudiant -> etudiant.getId())
                    .collect(Collectors.toSet()));
        }

        return dto;
    }

    private void updateCourseFromDTO(Course course, CourseCreateUpdateDTO dto) {
        course.setAbout(dto.getAbout());
        course.setCourselevel(dto.getCourselevel());
        course.setTitreCours(dto.getTitreCours());
        course.setDescription(dto.getDescription());
        course.setPrix(dto.getPrix());
        course.setDatePublication(dto.getDatePublication());
        course.setImage(dto.getImage());
        course.setLangage(dto.getLangage());
        course.setStatusCours(dto.getStatusCours());

        // Gestion de l'enseignant
        if (dto.getEnseignantId() != null) {
            Enseignant enseignant = enseignantRepository.findById(dto.getEnseignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Enseignant non trouvé avec l'ID: " + dto.getEnseignantId()));
            course.setEnseignant(enseignant);
        }

        // Gestion de la catégorie
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + dto.getCategoryId()));
            course.setCategory(category);
        } else {
            course.setCategory(null);
        }
    }
}
