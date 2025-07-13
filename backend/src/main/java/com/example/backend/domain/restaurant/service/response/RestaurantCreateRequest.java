package com.example.backend.domain.restaurant.service.response;

import com.example.backend.domain.restaurant.api.response.RestaurantDocument;


public record RestaurantCreateRequest(
        Long categoryId,
        String placeName,
        String roadAddressName,
        String longitude,
        String latitude
) {

    public static RestaurantCreateRequest of(RestaurantDocument doc, Long categoryId) {
        return new RestaurantCreateRequest(
                categoryId,
                doc.placeName(),
                doc.addressName(),
                doc.x(),
                doc.y()
        );
    }
}
