package com.example.backend.domain.restaurant.api;

import com.example.backend.domain.restaurant.api.response.KakaoPlacesSearchResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsPaginatedResponse;
import com.example.backend.domain.restaurant.service.RestaurantService;
import com.example.backend.domain.restaurant.service.response.RestaurantCreateRequest;
import com.example.backend.global.annotation.CurrentUserId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RestaurantResponse> getRestaurant(@CurrentUserId Long userId,
        @PathVariable("restaurantId") Long restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantByUserId(userId, restaurantId));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<Void> deleteRestaurant(@CurrentUserId Long userId,
        @PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(userId, restaurantId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build();
    }
}
