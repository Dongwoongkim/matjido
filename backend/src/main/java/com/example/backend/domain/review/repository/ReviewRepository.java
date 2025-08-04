package com.example.backend.domain.review.repository;

import com.example.backend.domain.review.entity.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r " +
        "JOIN FETCH r.user u " +
        "JOIN FETCH r.restaurant res " +
        "WHERE res.id = :restaurantId AND u.id = :reviewerId")
    Optional<Review> findByRestaurantIdAndReviewerId(@Param("restaurantId") Long restaurantId,
        @Param("reviewerId") Long reviewerId);
}
