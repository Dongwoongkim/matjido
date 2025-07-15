package com.example.backend.domain.review.api.response;

import com.example.backend.domain.review.entity.ReviewScore;

public record ReviewResponse(
    Long userId,
    Long reviewId,
    ReviewScore reviewScore,
    String title
) {

}
