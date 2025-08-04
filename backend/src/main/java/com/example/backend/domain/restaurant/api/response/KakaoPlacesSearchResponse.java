package com.example.backend.domain.restaurant.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KakaoPlacesSearchResponse(
        Meta meta,
        List<RestaurantDocument> documents
) {

    public record Meta(
            @JsonProperty("same_name") SameName sameName,
            @JsonProperty("pageable_count") int pageableCount,
            @JsonProperty("total_count") int totalCount,
            @JsonProperty("is_end") boolean isEnd
    ) {}

    public record SameName(
            List<String> region,
            String keyword,
            @JsonProperty("selected_region") String selectedRegion
    ) {}

    public record RestaurantDocument(
            String id,
            @JsonProperty("place_name") String placeName,
            @JsonProperty("category_name") String categoryName,
            @JsonProperty("category_group_code") String categoryGroupCode,
            @JsonProperty("category_group_name") String categoryGroupName,
            String phone,
            @JsonProperty("address_name") String addressName,
            @JsonProperty("road_address_name") String roadAddressName,
            String x,
            String y,
            @JsonProperty("place_url") String placeUrl,
            String distance
    ) {}
}
