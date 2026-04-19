package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.security.AuthUser;
import org.example.security.CurrentUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DebugController {

    private final CurrentUserService currentUserService;

    @GetMapping("/debug/me")
    public Map<String, Object> me() {
        return Map.of(
                "userId", currentUserService.userId(),
                "organizationId", currentUserService.companyId(),
                "admin", currentUserService.isAdmin()
        );
    }

    @GetMapping("/debug/auth")
    public Map<String, Object> auth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return Map.of(
                "authClass", auth.getClass().getName(),
                "principal", auth.getPrincipal(),
                "principalClass", auth.getPrincipal().getClass().getName(),
                "authorities", auth.getAuthorities()
        );
    }
}