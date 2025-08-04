package com.example.backend.domain.user.exception.handler;

import com.example.backend.domain.user.exception.UserNotFoundException;
import com.example.backend.global.response.ErrorResponse;
import com.example.backend.global.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.NOT_FOUND);
    }
}
