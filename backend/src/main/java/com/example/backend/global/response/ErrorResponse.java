package com.example.backend.global.response;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String code, String message) {

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status.value(), status.name(), message);
    }
}
