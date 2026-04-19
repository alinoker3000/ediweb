package org.example.dto.organization;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationCreateRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "\\d{13}", message = "GLN must consist 13 characters")
    private String gln;
}