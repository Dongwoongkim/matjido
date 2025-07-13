package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.CategoryResponse;
import com.example.backend.domain.restaurant.entity.Category;
import com.example.backend.domain.restaurant.service.CategoryService;
import com.example.backend.domain.restaurant.service.response.CategoryCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/categories")
@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryCreateRequest request) {
        Category category = categoryService.createCategory(request.name(), request.parentId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategoryResponse.from(category));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        CategoryResponse response = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
