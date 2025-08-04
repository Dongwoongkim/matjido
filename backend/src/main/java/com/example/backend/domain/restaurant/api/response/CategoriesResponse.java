package com.example.backend.domain.restaurant.api.response;

import com.example.backend.domain.restaurant.entity.Category;

import java.util.List;

public record CategoriesResponse(
        List<CategoryResponse> categories
) {
    public static CategoriesResponse from(List<Category> categories) {
        List<CategoryResponse> list = categories.stream()
                .map(CategoryResponse::from)
                .toList();
        return new CategoriesResponse(list);
    }
}