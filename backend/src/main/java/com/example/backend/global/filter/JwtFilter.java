package com.example.backend.global.filter;

import com.example.backend.global.jwt.JwtProvider;
import com.example.backend.global.response.TokenValidateResponse;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String accessToken = request.getHeader(AUTHORIZATION_HEADER);

        if (accessToken != null && !accessToken.isEmpty()) {
            TokenValidateResponse accessTokenValidateResponse = jwtProvider.validate(accessToken);

            if (accessTokenValidateResponse == TokenValidateResponse.VALID) {
                Authentication authentication = jwtProvider.createAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            if (accessTokenValidateResponse == TokenValidateResponse.EXPIRED) {
                // TODO : refreshToken + 리다이렉션으로 accessToken 재발급
            }

            filterChain.doFilter(request, response);
        }
    }
}
