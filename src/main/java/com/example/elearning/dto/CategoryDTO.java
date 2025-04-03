package com.example.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String titre;
    private String description;
    private String icon;
    private Long parentCategoryId;
    private Set<CategoryDTO> subCategories = new HashSet<>();

    private List<Long> courseIds;
    private boolean hasCourses;
    private boolean hasSubCategories;
}
