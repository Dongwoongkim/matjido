package com.example.backend.domain.restaurant.service;

import com.example.backend.domain.restaurant.api.response.KakaoPlacesSearchResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsPaginatedResponse;
import com.example.backend.domain.restaurant.api.response.RestaurantsResponse;
import com.example.backend.domain.restaurant.client.KakaoSearchApiClient;
import com.example.backend.domain.restaurant.entity.Category;
import com.example.backend.domain.restaurant.entity.Restaurant;
import com.example.backend.domain.restaurant.entity.vo.Address;
import com.example.backend.domain.restaurant.entity.vo.GeoLocation;
import com.example.backend.domain.restaurant.exception.CategoryNotFoundException;
import com.example.backend.domain.restaurant.exception.RestaurantNotFoundException;
import com.example.backend.domain.restaurant.repository.CategoryRepository;
import com.example.backend.domain.restaurant.repository.RestaurantRepository;
import com.example.backend.domain.restaurant.service.response.RestaurantCreateRequest;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.exception.UserNotFoundException;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final KakaoSearchApiClient kakaoSearchApiClient;

    public KakaoPlacesSearchResponse search(String query) {
        return kakaoSearchApiClient.searchRestaurants(query);
    }

    @Transactional
    public void addRestaurant(Long userId, RestaurantCreateRequest restaurantCreateRequest) {
        User user = findUser(userId);

        Category category = categoryRepository.findById(restaurantCreateRequest.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(restaurantCreateRequest.categoryId()));

        Restaurant restaurant = Restaurant.builder()
                .category(category)
                .placeName(restaurantCreateRequest.placeName())
                .address(Address.from(restaurantCreateRequest.roadAddressName()))
                .geoLocation(GeoLocation.of(restaurantCreateRequest.longitude(), restaurantCreateRequest.latitude()))
                .user(user)
                .build();

        restaurantRepository.save(restaurant);
    }

    public RestaurantsResponse getRestaurantsByUserId(Long userId) {
        List<Restaurant> restaurants = restaurantRepository.findAllByUserId(userId);
        return RestaurantsResponse.from(restaurants);
    }

    public RestaurantsPaginatedResponse getRestaurantsByUserId(Long userId, String categoryName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        if (categoryName != null && !categoryName.isBlank()) {
            return RestaurantsPaginatedResponse.from(restaurantRepository.findByUserIdAndCategoryName(userId, categoryName, pageable));
        }

        return RestaurantsPaginatedResponse.from(restaurantRepository.findAllByUserId(userId, pageable));
    }

    public RestaurantResponse getRestaurantByUserId(Long userId, Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId, userId);
        return RestaurantResponse.from(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long userId, Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId, userId);
        restaurantRepository.delete(restaurant);
    }

    private Restaurant findRestaurant(Long restaurantId, Long userId) {
        return restaurantRepository.findByIdAndUserId(restaurantId, userId)
                .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
