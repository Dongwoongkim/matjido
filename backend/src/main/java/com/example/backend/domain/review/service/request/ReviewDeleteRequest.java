package com.example.backend.domain.review.service.request;

import com.example.backend.global.annotation.CurrentUserId;

public record ReviewDeleteRequest(
    @CurrentUserId Long userId,
    Long restaurantId
) {

}
