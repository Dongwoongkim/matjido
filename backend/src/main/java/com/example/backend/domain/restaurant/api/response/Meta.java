package com.example.backend.domain.restaurant.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(
        @JsonProperty("same_name")
        SameName sameName,
        @JsonProperty("pageable_count")
        int pageableCount,
        @JsonProperty("total_count")
        int totalCount,
        @JsonProperty("is_end")
        boolean isEnd
) {}
