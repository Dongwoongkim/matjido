package com.example.backend.domain.restaurant.exception.handler;

import com.example.backend.domain.restaurant.exception.CategoryDepthExceededException;
import com.example.backend.domain.restaurant.exception.CategoryNotFoundException;
import com.example.backend.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.backend.global.response.ErrorResponse;
import com.example.backend.global.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestaurantExceptionHandler {

    @ExceptionHandler(CategoryDepthExceededException.class)
    public ResponseEntity<ErrorResponse> handleCategoryDepthExceededException(CategoryDepthExceededException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.NOT_FOUND);
    }
}
