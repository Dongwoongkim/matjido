package com.example.backend.domain.user.service;

import com.example.backend.domain.auth.service.response.KakaoUserInfoResponse;
import com.example.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public KakaoUserInfoResponse getUserInfo(Long memberId) {
        return KakaoUserInfoResponse.from(userRepository.findById(memberId)
            .orElseThrow(RuntimeException::new)
            .getEmail()
        );
    }
}
