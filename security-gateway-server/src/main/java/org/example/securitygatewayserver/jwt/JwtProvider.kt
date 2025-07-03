package org.example.securitygatewayserver.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.example.securitygatewayserver.domain.entity.RoleType
import org.example.securitygatewayserver.domain.entity.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
    private val properties: JwtProperties
) {
    private val signingKey: Key by lazy {
        Keys.hmacShaKeyFor(properties.secret.toByteArray(Charsets.UTF_8))
    }

    fun createToken(user: User, role: RoleType): String =
        Jwts.builder()
            .setSubject(user.id.toString())
            .claim("username", user.id)
            .claim("role", role.getAuthority())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + properties.token.accessExpiration))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

    fun validateToken(token: String): Jws<Claims> =
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw JwtException("만료된 토큰입니다", e)
        } catch (e: JwtException) {
            throw JwtException("토큰 형식이 잘못되었습니다", e)
        } catch (e: Exception) {
            throw JwtException("토큰을 검증할 수 없습니다", e)
        }

    fun getSubject(token: String): String =
        validateToken(token.removePrefix("Bearer ")).body.subject
}
