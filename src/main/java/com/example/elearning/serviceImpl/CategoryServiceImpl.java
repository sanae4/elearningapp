package com.example.elearning.serviceImpl;

import com.example.elearning.dto.CategoryCreateUpdateDTO;
import com.example.elearning.dto.CategoryDTO;
import com.example.elearning.model.Category;
import com.example.elearning.model.Course;
import com.example.elearning.repository.CategoryRepository;
import com.example.elearning.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::convertToDTOSafe)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findByIdWithSubCategoriesAndCourses(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        return convertToDTOSafe(category);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryCreateUpdateDTO categoryDTO) {
        Category category = new Category();
        updateCategoryFromDTO(category, categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return convertToDTOSafe(savedCategory);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryCreateUpdateDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + id));

        updateCategoryFromDTO(category, categoryDTO);
        Category updatedCategory = categoryRepository.save(category);
        return convertToDTOSafe(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Catégorie non trouvée avec l'ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getRootCategories() {
        return categoryRepository.findByParentCategoryIsNull().stream()
                .map(this::convertToDTOSafe)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getSubCategories(Long parentId) {
        List<Category> subCategories = categoryRepository.findByParentCategoryIdWithSubCategories(parentId);

        List<CategoryDTO> result = new ArrayList<>(subCategories.size());
        for (Category category : subCategories) {
            result.add(convertToDTOSafe(category));
        }
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public boolean hasSubCategories(Long categoryId) {
        return categoryRepository.hasSubCategories(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> searchCategoriesByTitle(String title) {
        List<Category> categories = categoryRepository.findByTitreContainingIgnoreCase(title);
        return categories.stream()
                .map(this::convertToDTOSafe)
                .collect(Collectors.toList());
    }

    // Méthodes utilitaires
    public CategoryDTO convertToDTOSafe(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setTitre(category.getTitre());
        dto.setDescription(category.getDescription());
        dto.setIcon(category.getIcon());

        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getId());
        }

        // Conversion des sous-catégories sans charger leurs relations
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            Set<CategoryDTO> subCategoriesDTO = new HashSet<>();
            for (Category subCategory : new ArrayList<>(category.getSubCategories())) {
                CategoryDTO subDto = new CategoryDTO();
                subDto.setId(subCategory.getId());
                subDto.setTitre(subCategory.getTitre());
                subDto.setDescription(subCategory.getDescription());
                subDto.setIcon(subCategory.getIcon());
                subCategoriesDTO.add(subDto);
            }
            dto.setSubCategories(subCategoriesDTO);
            dto.setHasSubCategories(true);
        } else {
            dto.setHasSubCategories(false);
        }

        // Conversion des cours sans charger leurs relations
        if (category.getCourses() != null && !category.getCourses().isEmpty()) {
            List<Long> courseIds = new ArrayList<>();
            for (Course course : new ArrayList<>(category.getCourses())) {
                courseIds.add(course.getId());
            }
            dto.setCourseIds(courseIds);
            dto.setHasCourses(true);
        } else {
            dto.setHasCourses(false);
        }

        return dto;
    }

    private void updateCategoryFromDTO(Category category, CategoryCreateUpdateDTO dto) {
        category.setTitre(dto.getTitre());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());

        // Gestion de la catégorie parente
        if (dto.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Catégorie parente non trouvée avec l'ID: " + dto.getParentCategoryId()));
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }
    }
}
