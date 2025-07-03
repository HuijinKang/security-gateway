package org.example.securitygatewayserver.security

import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.securitygatewayserver.jwt.JwtProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.removePrefix("Bearer ")

            try {
                val claims = jwtProvider.validateToken(token).body
                val username = claims["username"]?.toString()
                val role = claims["role"]?.toString()

                if (username != null && role != null) {
                    val auth = UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        listOf(SimpleGrantedAuthority(role))
                    ).apply {
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    }

                    SecurityContextHolder.getContext().authentication = auth
                }

            } catch (e: JwtException) {
                // 로그만 찍고 필터 체인 계속 (인가에서 거부되게)
                logger.warn("Invalid JWT: ${e.message}")
            }
        }
        System.err.println(authHeader)
        filterChain.doFilter(request, response)
    }
}
