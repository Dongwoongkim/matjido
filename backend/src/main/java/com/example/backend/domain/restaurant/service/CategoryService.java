package com.example.backend.domain.restaurant.service;

import com.example.backend.domain.restaurant.api.response.CategoriesResponse;
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

    public void createCategory(String name, Long parentId) {
        Category parent = isRoot(parentId) ? null : findParent(parentId);
        Category category = Category.of(name, parent);
        categoryRepository.save(category);
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

    public CategoriesResponse getCategories() {
        List<Category> allCategories = categoryRepository.findAllCategories();
        return CategoriesResponse.from(allCategories);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    private boolean isRoot(Long parentId) {
        return parentId == null;
    }
}
