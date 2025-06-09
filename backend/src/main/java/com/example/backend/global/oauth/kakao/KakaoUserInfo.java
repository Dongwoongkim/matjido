package com.example.backend.global.oauth.kakao;

import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KakaoUserInfo {

    public static final String KAKAO_ACCOUNT = "kakao_account";
    public static final String EMAIL = "email";

    private final Map<String, Object> attributes;

    public String getEmail() {
        Object kakaoAccountObj = attributes.get(KAKAO_ACCOUNT);
        Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoAccountObj;
        Object email = kakaoAccount.get(EMAIL);

        return email instanceof String ? (String) email : null;
    }
}
