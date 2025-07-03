package org.example.securitygatewayserver.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.jwt")
data class JwtProperties(
    val secret: String,
    val token: Token
) {
    data class Token(val accessExpiration: Long)
}
