package com.example.backend.global.jwt.refreshtoken.repository;

import com.example.backend.global.jwt.refreshtoken.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    RefreshToken findByEmail(String email);
}
