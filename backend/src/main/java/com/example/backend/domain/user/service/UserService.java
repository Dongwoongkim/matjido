package com.example.backend.domain.user.service;

import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getUserInfo(Long memberId) {
        return UserInfoResponse.from(userRepository.findById(memberId)
            .orElseThrow(RuntimeException::new)
            .getEmail()
        );
    }
}
