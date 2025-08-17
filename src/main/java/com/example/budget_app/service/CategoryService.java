package com.example.budget_app.service;

import com.example.budget_app.dto.CategoryDto;
import com.example.budget_app.excepiton.ResourceNotFoundException;
import com.example.budget_app.model.Category;
import com.example.budget_app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CategoryDto> findByType(Category.CategoryType type) {
        return categoryRepository.findByType(type).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToDto(category);
    }

    public CategoryDto save(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.name())) {
            throw new IllegalArgumentException("Category with name '" + categoryDto.name() + "' already exists");
        }
        
        Category category = mapToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Check if name is taken by another category
        if (!existingCategory.getName().equals(categoryDto.name()) 
                && categoryRepository.existsByName(categoryDto.name())) {
            throw new IllegalArgumentException("Category with name '" + categoryDto.name() + "' already exists");
        }

        existingCategory.setName(categoryDto.name());
        existingCategory.setDescription(categoryDto.description());
        existingCategory.setType(categoryDto.type());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return mapToDto(updatedCategory);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    private CategoryDto mapToDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getType()
        );
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        category.setType(categoryDto.type());
        return category;
    }
}
