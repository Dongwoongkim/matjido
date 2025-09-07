package com.example.backend.domain.review.api;

import com.example.backend.domain.review.api.response.ReviewDetailResponse;
import com.example.backend.domain.review.service.ReviewService;
import com.example.backend.domain.review.service.request.ReviewCreateRequest;
import com.example.backend.global.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ReviewDetailResponse> getReview(
        @PathVariable("restaurantId") Long restaurantId
    ) {
        return ResponseEntity.ok(reviewService.getReviewByRestaurantId(restaurantId));
    }

    @PostMapping
    public ResponseEntity<Void> createReview(
        @CurrentUserId Long userId,
        @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        reviewService.addReview(userId, reviewCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteReview(
        @CurrentUserId Long userId,
        @PathVariable("restaurantId") Long restaurantId
    ) {
        reviewService.deleteReview(userId, restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }
}
