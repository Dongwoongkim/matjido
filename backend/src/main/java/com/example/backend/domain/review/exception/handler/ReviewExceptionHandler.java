package com.example.backend.domain.review.exception.handler;

import com.example.backend.domain.review.exception.DuplicateReviewException;
import com.example.backend.domain.review.exception.InvalidReviewScoreException;
import com.example.backend.domain.review.exception.ReviewNotFoundException;
import com.example.backend.domain.review.exception.UnauthorizedReviewAccessException;
import com.example.backend.global.response.ErrorResponse;
import com.example.backend.global.util.ErrorResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateReviewException(DuplicateReviewException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidReviewScoreException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReviewScoreException(InvalidReviewScoreException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedReviewAccessException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedReviewAccessException(UnauthorizedReviewAccessException e) {
        return ErrorResponseUtil.getResponse(e, HttpStatus.UNAUTHORIZED);
    }
}
