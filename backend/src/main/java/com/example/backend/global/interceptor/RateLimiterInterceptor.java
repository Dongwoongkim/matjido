package com.example.backend.global.interceptor;

import com.example.backend.global.util.IpUtil;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnstableApiUsage")
@Slf4j
@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private static final double REQUESTS_PER_SECOND = 5.0;
    private static final long LOG_INTERVAL = TimeUnit.MINUTES.toMillis(1);

    private final ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> lastLoggedTime = new ConcurrentHashMap<>();

    @Value("${rate.limit.enabled:false}")
    private boolean rateLimitEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!rateLimitEnabled) {
            return true;
        }

        String ip = IpUtil.getClientIp(request);
        long now = System.currentTimeMillis();

        if (!isRequestAllowed(ip)) {
            if (isLogIntervalElapsed(ip, now)) log.warn("Rate limit exceeded. ip: {}", ip);
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests");
            return false;
        }

        if (isLogIntervalElapsed(ip, now)) log.info("Rate limit passed. ip: {}", ip);
        return true;
    }

    private boolean isRequestAllowed(String ip) {
        RateLimiter rateLimiter = rateLimiters.computeIfAbsent(ip, k -> RateLimiter.create(REQUESTS_PER_SECOND));
        return rateLimiter.tryAcquire();
    }

    private boolean isLogIntervalElapsed(String ip, long now) {
        Long last = lastLoggedTime.get(ip);

        if (last == null || now - last > LOG_INTERVAL) {
            lastLoggedTime.put(ip, now);
            return true;
        }

        return false;
    }
}
