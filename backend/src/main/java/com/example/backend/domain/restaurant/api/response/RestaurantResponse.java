package com.example.backend.domain.restaurant.api.response;

import com.example.backend.domain.restaurant.entity.Restaurant;

public record RestaurantResponse(
        Long id,
        String placeName,
        String city,
        String district,
        String roadName,
        String longitude,
        String latitude,
        Long categoryId,
        String categoryName
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getPlaceName(),
                restaurant.getAddress().getCity(),
                restaurant.getAddress().getDistrict(),
                restaurant.getAddress().getRoadName(),
                restaurant.getGeoLocation().getLongitude(),
                restaurant.getGeoLocation().getLatitude(),
                restaurant.getCategory().getId(),
                restaurant.getCategory().getName()
        );
    }
}
