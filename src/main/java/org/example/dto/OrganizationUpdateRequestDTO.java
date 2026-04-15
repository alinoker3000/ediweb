package org.example.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationUpdateRequestDTO {

    @Size(min = 1, message = "Name cannot be empty")
    private String name;

    @Pattern(regexp = "\\d{13}", message = "GLN must consist 13 characters")
    private String gln;
}