package com.example.backend.global.oauth.kakao;

import com.example.backend.domain.user.model.User;
import com.example.backend.domain.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoUserDetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        KakaoUserInfo userInfo = new KakaoUserInfo(oauth2User.getAttributes());

        User user = userRepository.findByEmail(userInfo.getEmail()).orElseGet(
            () ->
                userRepository.save(
                    User.create(userInfo.getEmail())
                )
        );

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        return new KakaoUserDetails(user.getEmail(),
            Collections.singletonList(authority), // 단일 authority 이므로 싱글톤으로 생성
            oauth2User.getAttributes());
    }
}
