package com.example.backend.domain.auth.service.response;

import lombok.Builder;

@Builder
public record KakaoLoginResponse(String accessToken, String refreshToken) {

}
