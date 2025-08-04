package com.example.backend.domain.restaurant.service.response;

import jakarta.annotation.Nullable;

public record CategoryCreateRequest(
        String name,
        @Nullable Long parentId
) {

}
