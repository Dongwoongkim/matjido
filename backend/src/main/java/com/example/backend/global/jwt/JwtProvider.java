package com.example.backend.global.jwt;

import com.example.backend.domain.user.service.response.KakaoLoginResponse;
import com.example.backend.global.oauth.kakao.KakaoUserDetails;
import com.example.backend.global.response.TokenValidateResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements InitializingBean {

    private final String secretKey;
    private final long accessTokenExpirationMs;
    private final long refreshTokenExpirationMs;
    private Key key;

    public JwtProvider(
        @Value("${spring.jwt.secret-key}") String secretKey,
        @Value("${spring.jwt.access-token-validity-in-seconds}") long accessTokenExpirationSeconds,
        @Value("${spring.jwt.refresh-token-validity-in-seconds}") long refreshTokenExpirationSeconds
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMs = accessTokenExpirationSeconds * 1000;
        this.refreshTokenExpirationMs = refreshTokenExpirationSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public KakaoLoginResponse issueToken(Long memberId, String email, String role) {
        Date now = new Date();
        Date accessTokenExpirationTime = new Date(now.getTime() + accessTokenExpirationMs);
        Date refreshTokenExpirationTime = new Date(now.getTime() + refreshTokenExpirationMs);

        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put("MEMBER_ID", memberId);
        accessClaims.put("EMAIL", email);
        accessClaims.put("AUTHORITY", role);

        Map<String, Object> refreshClaims = new HashMap<>();
        refreshClaims.put("tokenType", "refresh");

        return KakaoLoginResponse.builder().
            accessToken(Jwts.builder()
                .setClaims(accessClaims)
                .setExpiration(accessTokenExpirationTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
            )
            .refreshToken(Jwts.builder()
                .setClaims(refreshClaims)
                .setExpiration(refreshTokenExpirationTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
            )
            .build();
    }

    public Authentication createAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        String authority = claims.get("AUTHORITY", String.class);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));

        KakaoUserDetails principal = KakaoUserDetails.of(
            claims.get("MEMBER_ID", Long.class),
            claims.get("EMAIL", String.class),
            authorities,
            Map.of()
        );

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public TokenValidateResponse validate(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return TokenValidateResponse.VALID;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return TokenValidateResponse.INVALID;
        } catch (ExpiredJwtException e) {
            return TokenValidateResponse.EXPIRED;
        }
    }
}