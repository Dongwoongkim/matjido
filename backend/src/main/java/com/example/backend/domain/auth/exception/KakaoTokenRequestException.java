package com.example.backend.domain.auth.exception;

public class KakaoTokenRequestException extends RuntimeException {

    public KakaoTokenRequestException() {
        super("카카오 서버의 토큰 요청 과정에서 오류가 발생했습니다. 관리자에게 문의해주세요.");
    }
}