package com.example.backend.domain.user.api;

import com.example.backend.domain.auth.service.response.KakaoUserInfoResponse;
import com.example.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{memberId}")
    public ResponseEntity<KakaoUserInfoResponse> loginKakao(@PathVariable("memberId") Long memberId) {
        return new ResponseEntity<>(userService.getUserInfo(memberId), HttpStatus.OK);
    }
}
