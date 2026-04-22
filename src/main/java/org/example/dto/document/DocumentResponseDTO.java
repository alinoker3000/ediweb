package org.example.dto.document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponseDTO {

    private Long id;

    private String number;

    private String type;
    private String format;

    private Long senderId;
    private Long receiverId;

    private String data;
}
