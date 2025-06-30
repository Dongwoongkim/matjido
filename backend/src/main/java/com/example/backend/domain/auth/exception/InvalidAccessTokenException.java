package com.example.backend.domain.auth.exception;

public class InvalidAccessTokenException extends RuntimeException {

    private static final String DEFAULT_MSG = "유효하지 않은 액세스 토큰입니다.";

    public InvalidAccessTokenException() {
        super(DEFAULT_MSG);
    }
}
