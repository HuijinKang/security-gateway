package org.example.securitygatewayserver.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.securitygatewayserver.domain.service.UserService;
import org.example.securitygatewayserver.presentation.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest request) {
        userService.register(request);
        return ResponseEntity.ok("회원가입 완료");
    }
}
