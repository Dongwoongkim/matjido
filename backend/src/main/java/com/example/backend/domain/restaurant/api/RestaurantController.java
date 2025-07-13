package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.RestaurantSearchResponse;
import com.example.backend.domain.restaurant.service.RestaurantService;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import com.example.backend.global.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping
//    public void addRestaurant(@RequestBody RestaurantCreateRequest restaurantCreateRequest,
//                              @CurrentUserId Long userId) {
//    }
}
