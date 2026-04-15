package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequestDTO {

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Login cannot be empty")
    @Size(min = 3, max = 50)
    private String login;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, max = 100)
    private String password;

    private boolean admin;

    @NotNull(message = "OrganizationId is required")
    private Long organizationId;
}
