package com.example.backend.domain.review.exception;

public class IllegalReviewCreateException extends RuntimeException {

    public IllegalReviewCreateException() {
        super("리뷰를 생성할 수 있는 권한이 없습니다.");
    }
}
