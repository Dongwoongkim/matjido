package com.example.backend.domain.user.api;

import com.example.backend.domain.user.service.LoginService;
import com.example.backend.domain.user.service.response.KakaoLoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final LoginService loginService;

    @GetMapping("/kakao-login/callback")
    public ResponseEntity<KakaoLoginResponse> loginKakao(@RequestParam(name = "code") String code)
        throws JsonProcessingException {
        return new ResponseEntity<>(loginService.kakaoLogin(code), HttpStatus.OK);
    }
}
