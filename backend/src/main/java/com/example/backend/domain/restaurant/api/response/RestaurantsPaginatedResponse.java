package com.example.backend.domain.restaurant.api.response;

import com.example.backend.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;

import java.util.List;

public record RestaurantsPaginatedResponse(
        List<RestaurantResponse> content,
        long totalElements,
        int totalPages
) {
    public static RestaurantsPaginatedResponse from(Page<Restaurant> restaurants) {
        List<RestaurantResponse> content = restaurants.getContent()
                .stream()
                .map(RestaurantResponse::from)
                .toList();

        return new RestaurantsPaginatedResponse(
                content,
                restaurants.getTotalElements(),
                restaurants.getTotalPages()
        );
    }
}
