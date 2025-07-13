package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantSearchResponse;
import com.example.backend.domain.restaurant.service.RestaurantService;
import com.example.backend.domain.restaurant.service.response.RestaurantCreateRequest;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import com.example.backend.global.annotation.CurrentUserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<RestaurantSearchResponse> searchRestaurants(
            @Validated @ModelAttribute RestaurantSearchRequest request) {
        return ResponseEntity.ok(restaurantService.search(request));
    }

    @PostMapping
    public ResponseEntity<Void> addRestaurant(@RequestBody @Valid RestaurantCreateRequest request,
                                              @CurrentUserId Long userId) {
        restaurantService.addRestaurant(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<RestaurantResponse>> getRestaurantsByUserId(@CurrentUserId Long userId) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByUserId(userId));
    }

    @GetMapping("/me/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantByUserId(@CurrentUserId Long userId,
                                                              @PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantByUserId(userId, restaurantId));
    }
}
