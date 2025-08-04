package com.example.backend.domain.restaurant.repository;

import com.example.backend.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByUserId(Long userId);

    Page<Restaurant> findAllByUserId(Long userId, Pageable pageable);

    @Query(
            "select r from Restaurant r " +
            "where r.user.id = :userId " +
            "and (:categoryName is null or r.category.name = :categoryName)"
    )
    Page<Restaurant> findByUserIdAndCategoryName(Long userId, String categoryName, Pageable pageable);

    Optional<Restaurant> findByIdAndUserId(Long id, Long userId);
}
