package com.example.backend.domain.restaurant.repository;

import com.example.backend.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByUserId(Long userId);

    Optional<Restaurant> findByIdAndUserId(Long id, Long userId);
}
