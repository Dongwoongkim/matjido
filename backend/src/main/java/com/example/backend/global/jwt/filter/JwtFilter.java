package com.example.backend.global.jwt.filter;

import com.example.backend.global.jwt.JwtProvider;
import com.example.backend.global.jwt.refreshtoken.RefreshToken;
import com.example.backend.global.jwt.refreshtoken.repository.RefreshTokenRedisRepository;
import com.example.backend.global.jwt.response.JwtValidateResponse;
import com.example.backend.global.jwt.service.LogoutTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
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
    private final LogoutTokenService logoutTokenService;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/auth/kakao-login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String bearerAccessToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerAccessToken != null && !bearerAccessToken.isEmpty()) {
            String accessToken = bearerAccessToken.substring("Bearer ".length());
            JwtValidateResponse accessTokenValidateResponse = jwtProvider.validate(accessToken);

            if (accessTokenValidateResponse == JwtValidateResponse.VALID) {
                if (logoutTokenService.hasKey("blacklist:" + accessToken)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"message\": \"로그아웃된 요청입니다.\"}");
                    return;
                }

                Authentication authentication = jwtProvider.createAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            if (accessTokenValidateResponse == JwtValidateResponse.EXPIRED) {
                String email = jwtProvider.getEmail(accessToken);
                Optional<RefreshToken> refreshToken = refreshTokenRedisRepository.findById(email);

                if (refreshToken.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"message\": \"재로그인이 필요한 요청입니다.\"}");
                    return;
                }

                String newAccessToken = jwtProvider.issueNewAccessToken(refreshToken.get().getRefreshToken());

                Authentication authentication = jwtProvider.createAuthentication(newAccessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.setHeader("Authorization", "Bearer " + newAccessToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
