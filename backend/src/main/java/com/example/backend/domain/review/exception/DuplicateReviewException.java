package com.example.backend.domain.review.exception;

public class DuplicateReviewException extends RuntimeException {

    public DuplicateReviewException(Long restaurantId) {
        super(restaurantId + "번 식당에 대한 리뷰가 이미 존재합니다.");
    }
}
