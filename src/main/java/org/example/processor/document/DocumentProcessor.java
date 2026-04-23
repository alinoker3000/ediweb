package org.example.processor.document;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.entity.DocumentType;
import org.example.util.DocumentFormat;

public interface DocumentProcessor {

    DocumentFormat supports();

    void validate(String xml, DocumentType type);

    String generate(DocumentType type, JsonNode payload);
}