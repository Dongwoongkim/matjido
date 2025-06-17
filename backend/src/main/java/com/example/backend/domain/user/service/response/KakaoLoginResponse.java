package com.example.backend.domain.user.service.response;

import lombok.Builder;

@Builder
public record KakaoLoginResponse(String accessToken, String refreshToken) {

}
