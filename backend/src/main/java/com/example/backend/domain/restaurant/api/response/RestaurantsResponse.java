package com.example.backend.domain.restaurant.api.response;

import com.example.backend.domain.restaurant.entity.Restaurant;

import java.util.List;

public record RestaurantsResponse(
        List<RestaurantResponse> restaurants
) {
    public static RestaurantsResponse from(List<Restaurant> restaurants) {
        List<RestaurantResponse> responses = restaurants.stream()
                .map(RestaurantResponse::from)
                .toList();

        return new RestaurantsResponse(responses);
    }
}
