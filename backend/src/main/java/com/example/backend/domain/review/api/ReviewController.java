package com.example.backend.domain.review.api;

import com.example.backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

//    @GetMapping("/{restaurantId}")
//    public ReviewResponse getPagingReviewByRestaurantId(
//        @PathVariable Long restaurantId
//    ) {
//        reviewService.
//    }

//    @GetMapping("/{restaurantId}/{reviewerId}")
//    public ReviewDetailResponse getDetailReview(
//        @PathVariable Long restaurantId,
//        @PathVariable Long reviewerId
//    ) {
//        return reviewService.getReviewByRestaurantId(restaurantId, reviewerId);
//    }

//    @PostMapping("/{restaurantId}")
//    public ReviewResponse createReview(
//        @PathVariable Long restaurantId,
//        @RequestBody ReviewCreateRequest reviewCreateRequest
//    ) {
//
//    }


}
