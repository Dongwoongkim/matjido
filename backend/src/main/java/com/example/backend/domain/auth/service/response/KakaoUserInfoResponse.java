package com.example.backend.domain.auth.service.response;

public record KakaoUserInfoResponse(String email) {

    public static KakaoUserInfoResponse from(String email) {
        return new KakaoUserInfoResponse(email);
    }
}
