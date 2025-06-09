package com.example.backend.global.oauth.kakao;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoUserDetails implements OAuth2User {

    private Long memberId;
    private String email;
    private List<SimpleGrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static KakaoUserDetails of(Long memberId, String email, List<SimpleGrantedAuthority> authorities, Map<String, Object> attributes) {
        KakaoUserDetails kakaoUserDetails = new KakaoUserDetails();
        kakaoUserDetails.memberId = memberId;
        kakaoUserDetails.email = email;
        kakaoUserDetails.authorities = authorities;
        kakaoUserDetails.attributes = attributes;

        return kakaoUserDetails;
    }

    @Override
    public String getName() {
        return email;
    }
}
