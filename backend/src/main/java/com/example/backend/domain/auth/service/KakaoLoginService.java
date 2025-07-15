package com.example.backend.domain.auth.service;

import com.example.backend.domain.auth.exception.InvalidAccessTokenException;
import com.example.backend.domain.auth.exception.InvalidAuthCodeException;
import com.example.backend.domain.auth.exception.KakaoTokenRequestException;
import com.example.backend.domain.auth.service.request.KakaoLoginRequest;
import com.example.backend.domain.auth.service.response.KakaoLoginResponse;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.jwt.JwtProvider;
import com.example.backend.global.jwt.refreshtoken.RefreshToken;
import com.example.backend.global.jwt.refreshtoken.repository.RefreshTokenRedisRepository;
import com.example.backend.global.jwt.response.JwtValidateResponse;
import com.example.backend.global.jwt.service.LogoutTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {

    private final JwtProvider jwtProvider;
    private final LogoutTokenService logoutTokenService;
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Transactional
    public KakaoLoginResponse login(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code);
        KakaoLoginRequest request = getKakaoUserInfo(accessToken);

        User user = userRepository.findByEmail(request.email())
            .orElseGet(() ->
                userRepository.save(User.from(request.email())));

        KakaoLoginResponse kakaoLoginResponse = jwtProvider.issueToken(user.getId(), user.getEmail(), user.getRole());

        refreshTokenRedisRepository.save(
            RefreshToken.builder()
                .email(request.email())
                .refreshToken(kakaoLoginResponse.refreshToken())
                .build()
        );

        return kakaoLoginResponse;
    }

    @Transactional
    public KakaoLoginResponse loginForPostman(String accessToken) throws JsonProcessingException {
        KakaoLoginRequest request = getKakaoUserInfo(accessToken);

        User user = userRepository.findByEmail(request.email())
            .orElseGet(() ->
                userRepository.save(User.from(request.email())));

        KakaoLoginResponse kakaoLoginResponse = jwtProvider.issueToken(user.getId(), user.getEmail(), user.getRole());

        refreshTokenRedisRepository.save(
            RefreshToken.builder()
                .email(request.email())
                .refreshToken(kakaoLoginResponse.refreshToken())
                .build()
        );

        return kakaoLoginResponse;
    }

    public void logout(String accessToken) {
        accessToken = accessToken.substring("Bearer ".length());

        if (jwtProvider.validate(accessToken) == JwtValidateResponse.INVALID) {
            throw new InvalidAccessTokenException();
        }

        long expiration = jwtProvider.getRemainingExpiration(accessToken);
        logoutTokenService.setWithTTL("blacklist:" + accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
    }

    private String getAccessToken(String code) {
        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoClientId);
        body.add("client_secret", kakaoClientSecret);  // 추가
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        String responseBody = restClient.post()
            .uri("https://kauth.kakao.com/oauth/token")
            .headers(headers -> {
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            })
            .body(body)
            .retrieve()
            .body(String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new KakaoTokenRequestException();
        }
    }

    private KakaoLoginRequest getKakaoUserInfo(final String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
            );
            String responseBody = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String email = jsonNode.get("kakao_account").get("email").asText();
            return new KakaoLoginRequest(email);
        } catch (HttpClientErrorException e) {
            throw new InvalidAuthCodeException();
        }
    }
}
