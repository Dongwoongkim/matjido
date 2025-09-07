package com.example.backend.domain.review.repository;

import com.example.backend.domain.review.entity.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r "
        + "WHERE r.restaurant.id = :restaurantId "
        + "AND r.isDeleted = false")
    Optional<Review> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}
