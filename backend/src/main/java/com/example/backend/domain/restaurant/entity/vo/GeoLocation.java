package com.example.backend.domain.restaurant.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class GeoLocation {

    private String longitude;
    private String latitude;

    public static GeoLocation of(String x, String y) {
        return new GeoLocation(x, y);
    }
}
