package com.example.backend.domain.restaurant.entity.vo;

import com.example.backend.domain.restaurant.exception.InvalidAddressFormatException;
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
        if (roadAddressName == null || roadAddressName.isBlank()) {
            throw new InvalidAddressFormatException("도로명 주소가 비어있습니다.");
        }

        String[] parts = roadAddressName.trim().split("\\s+");
        if (parts.length < 3) {
            throw new InvalidAddressFormatException("도로명 주소 형식이 올바르지 않습니다: " + roadAddressName);
        }

        final List<String> TWO_LEVEL_CITIES = List.of(
                "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종"
        );

        String first = parts[0];

        String city;
        String district;
        String roadName;

        // 특별시 / 광역시 / 세종시 : ex. 서울 관악구
        if (TWO_LEVEL_CITIES.contains(first)) {
            city = first;
            district = parts[1];
            roadName = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        } else {
            // 도 단위 주소: ex. 경기 수원시 팔달구
            city = first + " " + parts[1];
            district = parts[2];
            roadName = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        }

        return of(city, district, roadName);
    }
}
