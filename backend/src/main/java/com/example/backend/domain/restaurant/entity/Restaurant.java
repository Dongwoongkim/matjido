package com.example.backend.domain.restaurant.entity;

import com.example.backend.domain.restaurant.entity.vo.Address;
import com.example.backend.domain.restaurant.entity.vo.GeoLocation;
import com.example.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private Restaurant(Category category, String placeName, Address address, GeoLocation geoLocation, User user) {
        this.category = category;
        this.placeName = placeName;
        this.address = address;
        this.geoLocation = geoLocation;
        this.user = user;
    }

    public static Restaurant of(Category category, String placeName, Address address, GeoLocation geoLocation, User user) {
        return new Restaurant(category, placeName, address, geoLocation, user);
    }
}

