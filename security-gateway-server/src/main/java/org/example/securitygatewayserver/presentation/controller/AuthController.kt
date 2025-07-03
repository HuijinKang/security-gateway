package org.example.securitygatewayserver.presentation.controller

import org.example.securitygatewayserver.domain.dto.LoginResponse
import org.example.securitygatewayserver.domain.service.AuthService
import org.example.securitygatewayserver.presentation.dto.LoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val token = authService.login(request)
        return ResponseEntity.ok(token)
    }
}
