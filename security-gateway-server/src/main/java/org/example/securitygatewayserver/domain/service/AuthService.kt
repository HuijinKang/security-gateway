package org.example.securitygatewayserver.domain.service

import org.example.securitygatewayserver.domain.dto.LoginResponse
import org.example.securitygatewayserver.domain.entity.RoleType
import org.example.securitygatewayserver.infrastructure.repository.UserRepository
import org.example.securitygatewayserver.jwt.JwtProvider
import org.example.securitygatewayserver.presentation.dto.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val authenticationManager: AuthenticationManager,
) {

    fun login(request: LoginRequest): LoginResponse {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val auth = authenticationManager.authenticate(authToken)

        val username = auth.name

        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not found")

        val token = jwtProvider.createToken(user, RoleType.USER)

        return LoginResponse(token)
    }
}