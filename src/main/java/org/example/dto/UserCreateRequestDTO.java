package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDTO {

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private boolean admin;

    private Long organizationId;
}