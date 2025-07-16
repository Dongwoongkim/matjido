package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.KakaoPlacesSearchResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsResponse;
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

@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<KakaoPlacesSearchResponse> searchRestaurants(
            @Validated @ModelAttribute RestaurantSearchRequest restaurantSearchRequest) {
        return ResponseEntity.ok(restaurantService.search(restaurantSearchRequest));
    }

    @PostMapping
    public ResponseEntity<Void> addRestaurant(@RequestBody @Valid RestaurantCreateRequest restaurantCreateRequest,
                                              @CurrentUserId Long userId) {
        restaurantService.addRestaurant(userId, restaurantCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<RestaurantsResponse> getRestaurantsByUserId(@CurrentUserId Long userId) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByUserId(userId));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantByUserId(@CurrentUserId Long userId,
                                                                    @PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantByUserId(userId, restaurantId));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@CurrentUserId Long userId,
                                                 @PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(userId, restaurantId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
