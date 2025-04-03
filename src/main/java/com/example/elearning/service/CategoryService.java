package com.example.elearning.service;

import com.example.elearning.dto.CategoryCreateUpdateDTO;
import com.example.elearning.dto.CategoryDTO;
import com.example.elearning.model.Category;

import java.util.List;

public interface CategoryService {

    // Opérations CRUD de base
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryCreateUpdateDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryCreateUpdateDTO categoryDTO);
    void deleteCategory(Long id);

    // Méthodes spécifiques
    List<CategoryDTO> getRootCategories();
    List<CategoryDTO> getSubCategories(Long parentId);
    boolean hasSubCategories(Long categoryId);
    List<CategoryDTO> searchCategoriesByTitle(String title);
}
