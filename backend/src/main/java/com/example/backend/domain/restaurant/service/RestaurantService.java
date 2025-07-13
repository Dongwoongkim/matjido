package com.example.backend.domain.restaurant.service;

import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantSearchResponse;
import com.example.backend.domain.restaurant.client.KakaoSearchApiClient;
import com.example.backend.domain.restaurant.entity.Category;
import com.example.backend.domain.restaurant.entity.Restaurant;
import com.example.backend.domain.restaurant.entity.vo.Address;
import com.example.backend.domain.restaurant.entity.vo.GeoLocation;
import com.example.backend.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.backend.domain.restaurant.repository.CategoryRepository;
import com.example.backend.domain.restaurant.repository.RestaurantRepository;
import com.example.backend.domain.restaurant.service.response.RestaurantCreateRequest;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.UserNotFoundException;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final KakaoSearchApiClient kakaoSearchApiClient;

    public RestaurantSearchResponse search(RestaurantSearchRequest restaurantSearchRequest) {
        return kakaoSearchApiClient.searchRestaurants(restaurantSearchRequest);
    }

    public void addRestaurant(Long userId, RestaurantCreateRequest restaurantCreateRequest) {
        User user = findUser(userId);

        Category category = categoryRepository.findById(restaurantCreateRequest.categoryId())
                .orElseThrow();

        Restaurant restaurant = Restaurant.of(
                category,
                restaurantCreateRequest.placeName(),
                Address.from(restaurantCreateRequest.roadAddressName()),
                GeoLocation.of(restaurantCreateRequest.longitude(), restaurantCreateRequest.latitude()),
                user
        );

        restaurantRepository.save(restaurant);
    }

    public List<RestaurantResponse> getRestaurantsByUserId(Long userId) {
        return restaurantRepository.findAllByUserId(userId).stream()
                .map(RestaurantResponse::from)
                .toList();
    }

    public RestaurantResponse getRestaurantByUserId(Long userId, Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId, userId);
        return RestaurantResponse.from(restaurant);
    }

    private Restaurant findRestaurant(Long restaurantId, Long userId) {
        Restaurant foundRestaurant = restaurantRepository.findByIdAndUserId(restaurantId, userId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        return foundRestaurant;
    }

    private User findUser(Long userId) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return foundUser;
    }
}
