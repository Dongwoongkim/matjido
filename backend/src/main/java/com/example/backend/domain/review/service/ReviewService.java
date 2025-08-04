package com.example.backend.domain.review.service;

import com.example.backend.domain.restaurant.entity.Restaurant;
import com.example.backend.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.backend.domain.restaurant.repository.RestaurantRepository;
import com.example.backend.domain.review.api.response.ReviewDetailResponse;
import com.example.backend.domain.review.entity.Review;
import com.example.backend.domain.review.exception.IllegalReviewCreateException;
import com.example.backend.domain.review.exception.ReviewNotFoundException;
import com.example.backend.domain.review.repository.ReviewRepository;
import com.example.backend.domain.review.service.request.ReviewCreateRequest;
import com.example.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public ReviewDetailResponse getReviewByRestaurantId(Long restaurantId, Long reviewerId) {
        Review review = reviewRepository.findByRestaurantIdAndReviewerId(restaurantId, reviewerId)
            .orElseThrow(ReviewNotFoundException::new);
        return ReviewDetailResponse.of(review);
    }

    @Transactional
    public void addReview(ReviewCreateRequest reviewCreateRequest) {
        Restaurant restaurant = restaurantRepository.findById(reviewCreateRequest.restaurantId())
            .orElseThrow(() -> new RestaurantNotFoundException(reviewCreateRequest.restaurantId()));

        User user = restaurant.getUser();

        if (!user.isOwner(reviewCreateRequest.userId())) {
            throw new IllegalReviewCreateException();
        }

        Review review = Review.builder()
            .user(user)
            .restaurant(restaurant)
            .title(reviewCreateRequest.title())
            .content(reviewCreateRequest.content())
            .reviewScore(reviewCreateRequest.reviewScore())
            .build();

        reviewRepository.save(review);
    }
}
