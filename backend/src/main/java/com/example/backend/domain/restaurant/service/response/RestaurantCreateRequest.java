package com.example.backend.domain.restaurant.service.response;


public record RestaurantCreateRequest(
        Long categoryId,
        String placeName,
        String roadAddressName,
        String longitude,
        String latitude
) {

    public static RestaurantCreateRequest of(Long categoryId, String placeName, String roadAddressName, String longitude, String latitude) {
        return new RestaurantCreateRequest(
                categoryId,
                placeName,
                roadAddressName,
                longitude,
                latitude
        );
    }
}
