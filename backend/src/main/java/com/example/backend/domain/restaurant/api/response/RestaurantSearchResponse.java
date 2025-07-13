package com.example.backend.domain.restaurant.api.response;

import java.util.List;

public record RestaurantSearchResponse(
        Meta meta,
        List<RestaurantDocument> documents
) {}
