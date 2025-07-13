package com.example.backend.domain.restaurant.service;

import com.example.backend.domain.restaurant.api.response.RestaurantSearchResponse;
import com.example.backend.domain.restaurant.client.KakaoSearchApiClient;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final KakaoSearchApiClient kakaoSearchApiClient;

    public RestaurantSearchResponse search(RestaurantSearchRequest restaurantSearchRequest) {
        return kakaoSearchApiClient.searchRestaurants(restaurantSearchRequest);
    }
}
