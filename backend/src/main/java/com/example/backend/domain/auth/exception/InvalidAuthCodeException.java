package com.example.backend.domain.auth.exception;

public class InvalidAuthCodeException extends RuntimeException {

    private static final String DEFAULT_MSG = "카카오 인가 코드가 유효하지 않습니다.";

    public InvalidAuthCodeException() {
        super(DEFAULT_MSG);
    }
}
