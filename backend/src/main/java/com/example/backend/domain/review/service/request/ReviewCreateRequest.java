package com.example.backend.domain.review.service.request;

import com.example.backend.domain.review.entity.ReviewScore;
import com.example.backend.global.annotation.CurrentUserId;

public record ReviewCreateRequest(
    @CurrentUserId Long userId,
    Long restaurantId,
    String title,
    String content,
    ReviewScore reviewScore
) {

}
