package com.example.backend.domain.review.service.request;

import com.example.backend.global.annotation.CurrentUserId;

public record ReviewCreateRequest(
    @CurrentUserId Long memberId
) {

}
