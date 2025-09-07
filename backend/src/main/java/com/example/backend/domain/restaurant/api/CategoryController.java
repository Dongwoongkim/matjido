package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.CategoriesResponse;
import com.example.backend.domain.restaurant.api.response.CategoryResponse;
import com.example.backend.domain.restaurant.service.CategoryService;
import com.example.backend.domain.restaurant.service.response.CategoryCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(
        @RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest.name(), categoryCreateRequest.parentId());
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<CategoriesResponse> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }
}
