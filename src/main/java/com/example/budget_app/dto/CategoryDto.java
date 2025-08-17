package com.example.budget_app.dto;

import com.example.budget_app.model.Category;

public record CategoryDto(
        Long id,
        String name,
        String description,
        Category.CategoryType type
) {
}
