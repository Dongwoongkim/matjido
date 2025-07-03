package com.example.backend.domain.auth.exception;

public class InvalidAccessTokenException extends RuntimeException {

    public InvalidAccessTokenException() {
        super("유효하지 않은 요청입니다.");
    }
}
