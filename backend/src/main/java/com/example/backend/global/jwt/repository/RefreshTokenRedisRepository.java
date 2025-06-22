package com.example.backend.global.jwt.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    RefreshToken findByAccessToken(String accessToken);
    
}
