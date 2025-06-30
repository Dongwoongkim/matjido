package com.example.backend.domain.auth.exception;

public class KakaoTokenRequestException extends RuntimeException {

    private static final String DEFAULT_MSG = "카카오 서버의 토큰 요청 과정에서 오류가 발생했습니다.";

    public KakaoTokenRequestException() {
        super(DEFAULT_MSG);
    }
}