package org.example.securitygatewayserver.domain.service

import org.example.securitygatewayserver.domain.entity.RoleType
import org.example.securitygatewayserver.domain.entity.User
import org.example.securitygatewayserver.infrastructure.repository.UserRepository
import org.example.securitygatewayserver.presentation.dto.SignUpRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
){
    fun register(request: SignUpRequest) {
        val encodedPassword = passwordEncoder.encode(request.password)

        val user = User(
            request.username,
            encodedPassword,
            RoleType.USER)

        userRepository.save(user)
    }
}