package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateUpdateDTO {
    private String titre;
    private String description;
    private String icon;
    private Long parentCategoryId; // Optionnel, null si c'est une catégorie racine
}
