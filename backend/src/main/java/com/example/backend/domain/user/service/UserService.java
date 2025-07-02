package com.example.backend.domain.user.service;

import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse getInfo(Long userId) {
        return UserInfoResponse.from(
            userRepository.findById(userId)
                .orElseThrow()
        );
    }
}
