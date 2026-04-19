package org.example.security;

import lombok.Getter;

@Getter
public class AuthUser {

    private Long userId;
    private Long organizationId;
    private boolean admin;

    public AuthUser(Long userId, Long organizationId, boolean admin) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.admin = admin;
    }

}