package com.example.backend.global.jwt.service;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setWithTTL(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
