package com.example.backend.domain.review.service.request;

import com.example.backend.domain.review.entity.ReviewScore;

public record ReviewCreateRequest(
    Long restaurantId,
    String title,
    String content,
    ReviewScore reviewScore
) {

}
