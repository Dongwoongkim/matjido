package com.example.backend.domain.review.api;

import com.example.backend.domain.review.api.response.ReviewDetailResponse;
import com.example.backend.domain.review.service.ReviewService;
import com.example.backend.domain.review.service.request.ReviewCreateRequest;
import com.example.backend.domain.review.service.request.ReviewDeleteRequest;
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
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ReviewDetailResponse> getReview(
        @PathVariable Long restaurantId
    ) {
        return ResponseEntity.ok(reviewService.getReviewByRestaurantId(restaurantId));
    }
    
    @PostMapping
    public ResponseEntity<Void> createReview(
        @RequestBody ReviewCreateRequest reviewCreateRequest
    ) {
        reviewService.addReview(reviewCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReview(
        @RequestBody ReviewDeleteRequest reviewDeleteRequest
    ) {
        reviewService.deleteReview(reviewDeleteRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }
}
