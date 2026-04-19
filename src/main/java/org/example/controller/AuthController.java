package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.auth.AuthResponseDTO;
import org.example.dto.auth.LoginRequestDTO;
import org.example.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO req) {
        return authService.login(req);
    }
}