package org.example.dto.document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentCreateRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String number;

    @NotBlank
    private String type;

    @NotBlank
    private String format;

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @NotBlank
    private String data;
}