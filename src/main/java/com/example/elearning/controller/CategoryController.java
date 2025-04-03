package com.example.elearning.controller;

import com.example.elearning.dto.CategoryCreateUpdateDTO;
import com.example.elearning.dto.CategoryDTO;
import com.example.elearning.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);





    // Autres méthodes...

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        // Log pour déboguer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Requête sans token d'authentification valide");
        } else {
            logger.info("Requête avec token d'authentification: " + authHeader.substring(0, 20) + "...");
        }

        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryCreateUpdateDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryCreateUpdateDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryDTO>> getRootCategories() {
        return ResponseEntity.ok(categoryService.getRootCategories());
    }

    @GetMapping("/{parentId}/subcategories")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.getSubCategories(parentId));
    }

    @GetMapping("/{id}/has-subcategories")
    public ResponseEntity<Boolean> hasSubCategories(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.hasSubCategories(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoriesByTitle(@RequestParam String title) {
        return ResponseEntity.ok(categoryService.searchCategoriesByTitle(title));
    }
}
