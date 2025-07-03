package com.example.backend.domain.user.service.response;

import com.example.backend.domain.user.entity.User;

public record UserInfoResponse(Long memberId, String email) {

    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(user.getMemberId(), user.getEmail());
    }
}
