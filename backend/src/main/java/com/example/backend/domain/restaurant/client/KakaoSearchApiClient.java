package com.example.backend.domain.restaurant.client;

import com.example.backend.domain.restaurant.api.response.KakaoPlacesSearchResponse;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoSearchApiClient {

    private static final String KEYWORD_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private static final String RESTAURANT_GROUP_CODE = "FD6";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    public KakaoPlacesSearchResponse searchRestaurants(RestaurantSearchRequest request) {
        try {
            String uri = UriComponentsBuilder.fromUriString(KEYWORD_SEARCH_URL)
                    .queryParam("query", request.query())
                    .queryParam("category_group_code", RESTAURANT_GROUP_CODE)
                    .build()
                    .toUriString();

            RestClient restClient = RestClient.builder()
                    .defaultHeader("Authorization", "KakaoAK " + restApiKey)
                    .build();

            KakaoPlacesSearchResponse response = restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(KakaoPlacesSearchResponse.class);

            return response;

        } catch (RestClientException e) {
            log.error("exception: {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }
}
