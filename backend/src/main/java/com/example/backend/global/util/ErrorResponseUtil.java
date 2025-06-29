package com.example.backend.global.util;

import com.example.backend.global.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseUtil {

    public static ResponseEntity<ErrorResponse> getResponse(RuntimeException e, HttpStatus status) {
        return ResponseEntity
            .status(status)
            .body(ErrorResponse.of(status, e.getMessage()));
    }
}
