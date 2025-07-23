package com.example.backend.domain.restaurant.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(Long id) {
        super("해당 식당 정보를 찾을 수 없습니다. (id: " + id + ")");
    }
}
