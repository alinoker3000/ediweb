package org.example.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentUpdateRequestDTO {

    @Size(min = 1, message = "number cannot be empty")
    private String number;

    @Size(min = 1, message = "type cannot be empty")
    private String type;

    @Size(min = 1, message = "format cannot be empty")
    private String format;

    private Long senderId;
    private Long receiverId;

    @Size(min = 1, message = "data cannot be empty")
    private String data;
}