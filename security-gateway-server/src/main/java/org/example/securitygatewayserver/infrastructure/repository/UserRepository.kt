package org.example.securitygatewayserver.infrastructure.repository

import org.example.securitygatewayserver.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}
