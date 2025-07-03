package org.example.securitygatewayserver.presentation.controller

import org.example.securitygatewayserver.domain.service.UserService
import org.example.securitygatewayserver.presentation.dto.SignUpRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest): ResponseEntity<String> {
        userService.register(request)
        return ResponseEntity.ok("회원가입 완료")
    }
}