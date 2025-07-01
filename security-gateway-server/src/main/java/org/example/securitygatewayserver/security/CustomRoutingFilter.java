package org.example.securitygatewayserver.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1) // Spring Security 필터 이후 실행되도록 낮은 우선순위 설정
public class CustomRoutingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        boolean isApi = uri.startsWith("/api");
        HttpSession session = request.getSession(false);
        Object admin = (session != null) ? session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY) : null;

        // 1. API 요청 처리
        if (isApi) {
            // Spring Security가 인증 처리하므로, 여기서는 패스
            chain.doFilter(request, response);
            return;
        }

        // 2. /auth, /user 요청 처리
        if (uri.startsWith("/auth") || uri.startsWith("/user")) {
            // 로그인/로그아웃 API, 인증 정보 확인 등은 Spring Security로
            chain.doFilter(request, response);
            return;
        }

        // 3. 그 외 요청에 대해 세션 검사
        if (admin == null && !uri.equals("/") && !uri.equals("/login")) {
            response.sendRedirect("/login");
            return;
        }

        // 4. 로그인된 사용자이면 루트로 포워딩
        request.getRequestDispatcher("/").forward(request, response);
    }
}
