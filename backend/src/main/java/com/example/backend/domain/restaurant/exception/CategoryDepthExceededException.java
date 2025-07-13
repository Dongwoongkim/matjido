package com.example.backend.domain.restaurant.exception;

public class CategoryDepthExceededException extends RuntimeException {

    public CategoryDepthExceededException(int depth) {
        super("카테고리는 최대 3단계까지만 허용됩니다. 현재 depth: " + depth);
    }
}
