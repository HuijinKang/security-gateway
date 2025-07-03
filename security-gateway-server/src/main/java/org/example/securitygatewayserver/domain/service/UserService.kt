package org.example.securitygatewayserver.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.securitygatewayserver.domain.entity.User;
import org.example.securitygatewayserver.infrastructure.repository.UserRepository;
import org.example.securitygatewayserver.presentation.dto.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(SignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = new User(
                request.username(),
                encodedPassword,
                "ROLE_ADMIN"
        );

        userRepository.save(user);
    }
}
