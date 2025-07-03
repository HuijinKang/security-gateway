package org.example.securitygatewayserver.domain.entity

enum class RoleType {
    USER, ADMIN;

    fun getAuthority(): String {
        return "ROLE_$name"
    }
}