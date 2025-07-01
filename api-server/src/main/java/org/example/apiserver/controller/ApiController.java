package org.example.apiserver.controller;

import org.example.apiserver.aop.RequireRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @RequireRole(value = {"ROLE_ADMIN"})
    @GetMapping("/roleCheck")
    public String roleCheck() {
        return "권한 확인 완료";
    }
}
