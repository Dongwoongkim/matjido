package com.example.backend.global.oauth;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoUserDetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(request);
        String email = new KakaoUserInfo(oauthUser.getAttributes()).getEmail();

        User user = userRepository.findByEmail(email).get();

        return KakaoUserDetails.of(
            user.getMemberId(),
            user.getEmail(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole())),
            oauthUser.getAttributes()
        );
    }
}