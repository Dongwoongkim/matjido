package com.example.backend.domain.auth.exception.handler;

import com.example.backend.domain.auth.exception.InvalidAccessTokenException;
import com.example.backend.domain.auth.exception.InvalidAuthCodeException;
import com.example.backend.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidAuthCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthCode(InvalidAuthCodeException e) {
        HttpStatus status = HttpStatus.BAD_GATEWAY;
        return ResponseEntity
            .status(status)
            .body(ErrorResponse.of(status, e.getMessage()));
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccessToken(InvalidAccessTokenException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return ResponseEntity
            .status(status)
            .body(ErrorResponse.of(status, e.getMessage()));
    }
}
