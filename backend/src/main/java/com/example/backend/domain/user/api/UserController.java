package com.example.backend.domain.user.api;

import com.example.backend.domain.user.service.LoginService;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.domain.user.service.response.KakaoLoginResponse;
import com.example.backend.domain.user.service.response.UserInfoResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final LoginService loginService;
    private final UserService userService;

    @GetMapping("/kakao-login/callback")
    public ResponseEntity<KakaoLoginResponse> kakaologin(@RequestParam(name = "code") String code)
        throws JsonProcessingException {
        return new ResponseEntity<>(loginService.kakaoLogin(code), HttpStatus.OK);
    }

    @PostMapping("/kakao-logout")
    public ResponseEntity<Void> kakaoLogout(@RequestBody String accessToken) {
        loginService.kakaoLogout(accessToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<UserInfoResponse> loginKakao(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(userService.getUserInfo(memberId), HttpStatus.OK);
    }
}
