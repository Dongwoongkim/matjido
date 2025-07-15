package com.example.backend.domain.auth.api;

import com.example.backend.domain.auth.service.KakaoLoginService;
import com.example.backend.domain.auth.service.response.KakaoLoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/kakao-login/callback")
    public ResponseEntity<KakaoLoginResponse> login(@RequestParam(name = "code") String code)
        throws JsonProcessingException {
        return new ResponseEntity<>(kakaoLoginService.login(code), HttpStatus.OK);
    }

    @GetMapping("/kakao-login/postman/callback")
    public ResponseEntity<KakaoLoginResponse> loginForPostman(@RequestParam(name = "accessToken") String accessToken)
        throws JsonProcessingException {
        return new ResponseEntity<>(kakaoLoginService.loginForPostman(accessToken), HttpStatus.OK);
    }

    @PostMapping("/kakao-logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken) {
        kakaoLoginService.logout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
