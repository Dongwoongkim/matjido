package com.example.backend.global.jwt;

import com.example.backend.domain.user.model.Role;
import com.example.backend.global.oauth.kakao.KakaoUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider implements InitializingBean {

    private final String secretKey;
    private final long accessTokenExpirationMillis;
    private Key key;

    public JwtProvider(
        @Value("${jwt.secret-key}") String secretKey,
        @Value("${jwt.access-token-validity-in-seconds}") long accessTokenExpirationSeconds
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationMillis = accessTokenExpirationSeconds * 1000;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateAccessToken(Long memberId, String email, Role role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpirationMillis);

        Map<String, Object> claims = new HashMap<>();
        claims.put("MEMBER_ID", memberId);
        claims.put("EMAIL", email);
        claims.put("AUTHORITY", role.name());

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
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

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}