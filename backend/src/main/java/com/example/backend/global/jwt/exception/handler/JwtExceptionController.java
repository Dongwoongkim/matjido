package com.example.backend.global.jwt.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exception/authentication")
public class JwtExceptionController {

    @GetMapping("/entry-point")
    public ResponseEntity<String> entryPoint() {
        return new ResponseEntity("로그인이 필요한 요청입니다.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/access-denied")
    public ResponseEntity<String> accessDenied() {
        return new ResponseEntity("접근 불가능한 권한입니다.", HttpStatus.FORBIDDEN);
    }
}
