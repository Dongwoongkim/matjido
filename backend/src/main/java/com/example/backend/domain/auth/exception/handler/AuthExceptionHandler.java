package com.example.backend.domain.auth.exception.handler;

import com.example.backend.domain.auth.exception.InvalidAccessTokenException;
import com.example.backend.domain.auth.exception.InvalidAuthCodeException;
import com.example.backend.domain.auth.exception.KakaoTokenRequestException;
import com.example.backend.global.response.ErrorResponse;
import com.example.backend.global.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidAuthCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAuthCode(InvalidAuthCodeException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAccessToken(InvalidAccessTokenException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(KakaoTokenRequestException.class)
    public ResponseEntity<ErrorResponse> handleKakaoTokenRequestException(KakaoTokenRequestException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.BAD_GATEWAY);
    }
}
