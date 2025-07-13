package com.example.backend.domain.restaurant.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

    private String city;
    private String district;
    private String roadName;
}
