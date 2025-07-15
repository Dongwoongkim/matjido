package com.example.backend.domain.review.service;

import com.example.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
//
//    public ReviewDetailResponse getReviewByRestaurantId(Long restaurantId, Long reviewerId) {
//        Review review = reviewRepository.findByRestaurantIdAndReviewerId(restaurantId, reviewerId)
//            .orElseThrow(ReviewNotFoundException::new);
//        return ReviewDetailResponse.of(review);
//    }
}
