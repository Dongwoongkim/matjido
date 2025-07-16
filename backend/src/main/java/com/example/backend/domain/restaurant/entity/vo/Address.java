package com.example.backend.domain.restaurant.entity.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Embeddable
public class Address {

    private String city;
    private String district;
    private String roadName;

    private static Address of(String city, String district, String roadName) {
        return new Address(city, district, roadName);
    }

    public static Address from(String roadAddressName) {
        String[] parts = splitAddress(roadAddressName);
        String first = parts[0];

        if(isTwoLevelCity(first)) {
            return parseTwoLevelAddress(parts);
        }

        return parseProvinceLevelAddress(parts);
    }

    private static String[] splitAddress(String roadAddressName) {
        final String WHITESPACE_REGEX = "\\s+";
        String[] parts = roadAddressName.trim().split(WHITESPACE_REGEX);
        return parts;
    }

    private static boolean isTwoLevelCity(String cityName) {
        final List<String> TWO_LEVEL_CITIES = List.of(
                "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종"
        );
        return TWO_LEVEL_CITIES.contains(cityName);
    }

    private static Address parseTwoLevelAddress(String[] parts) {
        String city = parts[0];
        String district = parts[1];
        String roadName = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        return of(city, district, roadName);
    }

    private static Address parseProvinceLevelAddress(String[] parts) {
        String city = parts[0] + " " + parts[1];
        String district = parts[2];
        String roadName = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
        return of(city, district, roadName);
    }
}
