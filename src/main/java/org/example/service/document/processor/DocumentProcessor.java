package org.example.service.document.processor;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import org.example.entity.DocumentType;
import org.example.enums.DocumentFormat;

public interface DocumentProcessor {

    DocumentFormat supports();

    void validate(String xml, DocumentType type);

    String generate(DocumentType type, JsonNode payload);
}