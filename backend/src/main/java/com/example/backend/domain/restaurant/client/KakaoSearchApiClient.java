package com.example.backend.domain.restaurant.client;

import com.example.backend.domain.restaurant.api.response.RestaurantSearchResponse;
import com.example.backend.domain.restaurant.service.response.RestaurantSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoSearchApiClient {

    private static final String KEYWORD_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private static final String RESTAURANT_GROUP_CODE = "FD6";

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String restApiKey;

    private final RestTemplate restTemplate;

    public RestaurantSearchResponse searchRestaurants(RestaurantSearchRequest request) {
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(KEYWORD_SEARCH_URL)
                    .queryParam("query", request.query())
                    .queryParam("category_group_code", RESTAURANT_GROUP_CODE);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + restApiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<RestaurantSearchResponse> response = restTemplate.exchange(
                    uriBuilder.build().toUriString(),
                    HttpMethod.GET,
                    entity,
                    RestaurantSearchResponse.class
            );

            return response.getBody();

        } catch (RestClientException e) {
            log.error("exception: {}", e.getMessage(), e);
            throw new RestClientException(e.getMessage());
        }
    }
}
