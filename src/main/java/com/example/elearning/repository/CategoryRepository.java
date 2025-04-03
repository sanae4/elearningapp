package com.example.elearning.repository;

import com.example.elearning.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Trouver toutes les catégories racines (sans parent)
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.parentCategory IS NULL")
    List<Category> findByParentCategoryIsNull();

    // Trouver toutes les sous-catégories d'une catégorie parent
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.parentCategory.id = :parentId")
    List<Category> findByParentCategoryId(@Param("parentId") Long parentId);

    // Rechercher des catégories par titre
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE UPPER(c.titre) LIKE UPPER(CONCAT('%', :titre, '%'))")
    List<Category> findByTitreContainingIgnoreCase(@Param("titre") String titre);

    // Vérifier si une catégorie a des sous-catégories
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.parentCategory.id = :categoryId")
    boolean hasSubCategories(@Param("categoryId") Long categoryId);

    // Charger une catégorie avec ses sous-catégories en une seule requête
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.id = :id")
    Optional<Category> findByIdWithSubCategories(@Param("id") Long id);

    // Charger une catégorie avec ses sous-catégories et ses cours en une seule requête

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subCategories LEFT JOIN FETCH c.courses WHERE c.id = :id")
    Optional<Category> findByIdWithSubCategoriesAndCourses(@Param("id") Long id);
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.parentCategory.id = :parentId")
    List<Category> findByParentCategoryIdWithSubCategories(@Param("parentId") Long parentId);
    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.courses WHERE c.parentCategory.id = :parentId")
    List<Category> findSubCategoriesWithCourses(@Param("parentId") Long parentId);

}
