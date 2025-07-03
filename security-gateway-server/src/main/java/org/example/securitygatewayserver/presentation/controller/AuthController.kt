package org.example.securitygatewayserver.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.securitygatewayserver.domain.dto.LoginResponse;
import org.example.securitygatewayserver.domain.service.AuthService;
import org.example.securitygatewayserver.presentation.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 세션 방식
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        LoginResponse result = authService.login(request, httpRequest);

        return ResponseEntity.ok()
                .header("X-User-Id", result.username())
                .header("X-Role", result.role())
                .body("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("로그아웃 완료");
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getSession(Authentication authentication, HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("authorities: " + authentication.getAuthorities());
        return ResponseEntity.ok("session: "
                + session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
        ));
    }
}