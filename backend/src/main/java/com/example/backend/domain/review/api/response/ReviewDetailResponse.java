package com.example.backend.domain.review.api.response;

import com.example.backend.domain.review.entity.Review;
import com.example.backend.domain.review.entity.ReviewScore;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ReviewDetailResponse(
    Long memberId,
    Long reviewId,
    ReviewScore reviewScore,
    String title,
    String content,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt
) {

    public static ReviewDetailResponse of(Review review) {
        return new ReviewDetailResponse(review.getUser().getMemberId(),
            review.getReviewId(),
            review.getReviewScore(),
            review.getTitle(),
            review.getContent(),
            review.getCreatedAt());
    }
}
