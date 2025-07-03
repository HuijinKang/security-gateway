package org.example.securitygatewayserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class SecurityGatewayServerApplication

fun main(args: Array<String>) {
    runApplication<SecurityGatewayServerApplication>(*args)
}