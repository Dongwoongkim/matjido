package com.example.backend.domain.user.api;

import com.example.backend.domain.user.service.UserService;
import com.example.backend.domain.user.service.response.UserInfoResponse;
import com.example.backend.global.annotation.CurrentUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserInfoResponse> getInfo(@CurrentUserId Long userId) {
        return new ResponseEntity<>(userService.getInfo(userId), HttpStatus.OK);
    }
}
