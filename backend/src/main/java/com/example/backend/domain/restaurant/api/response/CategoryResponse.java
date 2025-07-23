package com.example.backend.domain.restaurant.api.response;

import com.example.backend.domain.restaurant.entity.Category;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        int depth,
        List<CategoryResponse> children
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDepth(),
                category.getChildren().stream()
                        .map(CategoryResponse::from)
                        .toList()
        );
    }
}
