package com.example.backend.global.jwt.filter;

import com.example.backend.global.jwt.JwtProvider;
import com.example.backend.global.jwt.response.JwtValidateResponse;
import com.example.backend.global.jwt.service.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/user/kakao-login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerAccessToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerAccessToken != null && !bearerAccessToken.isEmpty()) {
            String accessToken = bearerAccessToken.substring("Bearer ".length());
            JwtValidateResponse accessTokenValidateResponse = jwtProvider.validate(accessToken);

            if (accessTokenValidateResponse == JwtValidateResponse.VALID) {
                if (redisService.hasKey("blacklist:" + accessToken)) {
                    throw new RuntimeException("logged out access token");
                }

                Authentication authentication = jwtProvider.createAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            if (accessTokenValidateResponse == JwtValidateResponse.EXPIRED) {
                // TODO : VALIDATE EXPIRE REFRESH-TOKEN ON REDIS BY ACCESS TOKEN
                jwtProvider.validateExpireRefreshTokenFromAccessToken(accessToken);

            }

            filterChain.doFilter(request, response);
        }
    }
}
