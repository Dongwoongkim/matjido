package com.example.backend.domain.restaurant.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SameName(
        List<String> region,
        String keyword,
        @JsonProperty("selected_region")
        String selectedRegion
) {}
