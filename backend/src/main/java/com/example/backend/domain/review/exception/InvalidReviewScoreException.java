package com.example.backend.domain.review.exception;

public class InvalidReviewScoreException extends RuntimeException {

    public InvalidReviewScoreException() {
        super("유효하지 않은 리뷰점수입니다.");
    }
}
