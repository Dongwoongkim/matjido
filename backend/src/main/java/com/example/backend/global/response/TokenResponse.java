package com.example.backend.global.response;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken, String refreshToken) {

}
