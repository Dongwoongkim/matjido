package com.example.backend.domain.user.service.response;

public record UserInfoResponse(String email) {

    public static UserInfoResponse from(String email) {
        return new UserInfoResponse(email);
    }
}
