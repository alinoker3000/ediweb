package org.example.dto.document;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentUpdateRequestDTO {

    @Size(min = 1, message = "Number cannot be empty")
    private String number;

    @Size(min = 1, message = "Type cannot be empty")
    private String type;

    private Long senderId;
    private Long receiverId;

    @Size(min = 1, message = "Data cannot be empty")
    private JsonNode payload;
}