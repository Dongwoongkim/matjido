package com.example.backend.domain.review.service;

import com.example.backend.domain.restaurant.entity.Restaurant;
import com.example.backend.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.backend.domain.restaurant.repository.RestaurantRepository;
import com.example.backend.domain.review.api.response.ReviewDetailResponse;
import com.example.backend.domain.review.entity.Review;
import com.example.backend.domain.review.exception.DuplicateReviewException;
import com.example.backend.domain.review.exception.ReviewNotFoundException;
import com.example.backend.domain.review.exception.UnauthorizedReviewAccessException;
import com.example.backend.domain.review.exception.UnauthorizedReviewAccessException.ReviewAction;
import com.example.backend.domain.review.repository.ReviewRepository;
import com.example.backend.domain.review.service.request.ReviewCreateRequest;
import com.example.backend.domain.review.service.request.ReviewDeleteRequest;
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

    public ReviewDetailResponse getReviewByRestaurantId(Long restaurantId) {
        Review review = reviewRepository.findByRestaurantId(restaurantId)
            .orElseThrow(ReviewNotFoundException::new);
        return ReviewDetailResponse.of(review);
    }

    @Transactional
    public void addReview(ReviewCreateRequest reviewCreateRequest) {
        Restaurant restaurant = restaurantRepository.findById(reviewCreateRequest.restaurantId())
            .orElseThrow(() -> new RestaurantNotFoundException(reviewCreateRequest.restaurantId()));

        User user = restaurant.getUser();

        if (!user.isOwner(reviewCreateRequest.userId())) {
            throw new UnauthorizedReviewAccessException(ReviewAction.CREATE);
        }

        reviewRepository.findByRestaurantId(reviewCreateRequest.restaurantId())
            .ifPresent(r -> {
                throw new DuplicateReviewException(reviewCreateRequest.restaurantId());
            });

        Review review = Review.builder()
            .user(user)
            .restaurant(restaurant)
            .title(reviewCreateRequest.title())
            .content(reviewCreateRequest.content())
            .reviewScore(reviewCreateRequest.reviewScore())
            .build();

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(ReviewDeleteRequest reviewDeleteRequest) {
        Restaurant restaurant = restaurantRepository.findById(reviewDeleteRequest.restaurantId())
            .orElseThrow(() -> new RestaurantNotFoundException(reviewDeleteRequest.restaurantId()));

        User user = restaurant.getUser();

        if (!user.isOwner(reviewDeleteRequest.userId())) {
            throw new UnauthorizedReviewAccessException(ReviewAction.DELETE);
        }

        Review review = reviewRepository.findByRestaurantId(restaurant.getId())
            .orElseThrow(ReviewNotFoundException::new);

        review.delete();
    }
}
