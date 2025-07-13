package com.example.backend.domain.restaurant.service;

import com.example.backend.domain.restaurant.api.response.CategoryResponse;
import com.example.backend.domain.restaurant.entity.Category;
import com.example.backend.domain.restaurant.exception.CategoryNotFoundException;
import com.example.backend.domain.restaurant.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(String name, Long parentId) {
        Category parent = (parentId != null) ? findParent(parentId) : null;
        Category category = Category.of(name, parent);
        return categoryRepository.save(category);
    }

    private Category findParent(Long parentId) {
        return categoryRepository.findById(parentId)
                .orElseThrow(() -> new CategoryNotFoundException(parentId));
    }

    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> getCategories() {
        List<Category> allCategories = categoryRepository.findAllCategories();
        return allCategories.stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
