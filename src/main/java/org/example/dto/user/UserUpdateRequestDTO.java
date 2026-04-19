package org.example.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDTO {

    @Size( message = "First name cannot be empty")
    private String firstName;

    @Size(min = 1, message = "Last name cannot be empty")
    private String lastName;

    @Size(min = 3, max = 500, message = "Login must be at least 3 characters long")
    private String login;

    @Size(min = 4, max = 100, message = "Password must be at least 4 characters long")
    private String password;

    private boolean admin;

    private Long organizationId;

}
