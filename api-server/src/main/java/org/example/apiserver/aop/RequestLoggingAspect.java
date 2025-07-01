package org.example.apiserver.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestLoggingAspect {

    private final HttpServletRequest request;

    @Before("execution(* org.example..*Controller.*(..))")
    public void logRequestDetails() {
        String requestURL = request.getRequestURL().toString();

        String userId = request.getHeader("X-User-Id");
        String role = request.getHeader("X-Role");

        log.info("API Request - URL: {}, X-User-Id: {}, X-Role: {}", requestURL, userId, role);
    }
}
