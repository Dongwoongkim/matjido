package com.example.backend.domain.auth.exception;

public class InvalidAuthCodeException extends RuntimeException {

    public InvalidAuthCodeException() {
        super("카카오 인가 코드가 유효하지 않습니다. 관리자에게 문의해주세요.");
    }
}