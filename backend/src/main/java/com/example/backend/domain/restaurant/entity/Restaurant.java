package com.example.backend.domain.restaurant.entity;

import com.example.backend.domain.restaurant.entity.vo.Address;
import com.example.backend.domain.restaurant.entity.vo.GeoLocation;
import com.example.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    private String placeName;

    @Embedded
    private Address address;

    @Embedded
    private GeoLocation geoLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    private Restaurant(Category category, String placeName, Address address, GeoLocation geoLocation, User user) {
        this.category = category;
        this.placeName = placeName;
        this.address = address;
        this.geoLocation = geoLocation;
        this.user = user;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Long getCategoryId() {
        return category.getId();
    }

    public String getCity() {
        return address.getCity();
    }

    public String getDistrict() {
        return address.getDistrict();
    }

    public String getRoadName() {
        return address.getRoadName();
    }

    public String getLongitude() {
        return geoLocation.getLongitude();
    }

    public String getLatitude() {
        return geoLocation.getLatitude();
    }
}

