package org.example.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private boolean admin;

    private Long organizationId;
}