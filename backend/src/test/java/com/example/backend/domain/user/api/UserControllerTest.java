package com.example.backend.domain.user.api;

import com.example.backend.domain.user.service.response.UserInfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class UserControllerTest {

    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void read() {
        UserInfoResponse response = restClient.get()
            .uri("/api/user/{memberId}", 10L)
            .retrieve()
            .body(UserInfoResponse.class);

        System.out.println("response.email() = " + response.email());
    }

}