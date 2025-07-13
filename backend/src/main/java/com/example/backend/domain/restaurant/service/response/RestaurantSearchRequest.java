package com.example.backend.domain.restaurant.service.response;

import jakarta.validation.constraints.NotBlank;

public record RestaurantSearchRequest (
        @NotBlank String query
) {}
