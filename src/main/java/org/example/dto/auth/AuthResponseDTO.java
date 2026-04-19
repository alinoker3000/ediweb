package org.example.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Long userId;
    private Long organizationId;
    private boolean admin;
}