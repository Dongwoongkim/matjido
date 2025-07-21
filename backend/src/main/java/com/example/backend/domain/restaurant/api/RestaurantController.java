package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.KakaoPlacesSearchResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsPaginatedResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsResponse;
import com.example.backend.domain.restaurant.entity.Restaurant;
import com.example.backend.domain.restaurant.service.RestaurantService;
import com.example.backend.domain.restaurant.service.response.RestaurantCreateRequest;
import com.example.backend.global.annotation.CurrentUserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<KakaoPlacesSearchResponse> searchRestaurants(@RequestParam("query") String query) {
        return ResponseEntity.ok(restaurantService.search(query));
    }

    @PostMapping
    public ResponseEntity<Void> addRestaurant(@RequestBody @Valid RestaurantCreateRequest restaurantCreateRequest,
                                              @CurrentUserId Long userId) {
        restaurantService.addRestaurant(userId, restaurantCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<RestaurantsPaginatedResponse> getRestaurantsByUserId(
            @CurrentUserId Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String categoryName
    ) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByUserId(userId, categoryName, page, size));
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
