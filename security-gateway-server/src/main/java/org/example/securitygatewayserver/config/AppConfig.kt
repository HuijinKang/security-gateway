package org.example.securitygatewayserver.config

import org.example.securitygatewayserver.jwt.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
open class AppConfig