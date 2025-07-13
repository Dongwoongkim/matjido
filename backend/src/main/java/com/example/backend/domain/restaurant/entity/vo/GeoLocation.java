package com.example.backend.domain.restaurant.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class GeoLocation {

    private String longitude;
    private String latitude;
}
