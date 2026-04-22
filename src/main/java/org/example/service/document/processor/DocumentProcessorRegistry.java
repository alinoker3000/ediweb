package org.example.service.document.processor;

import org.example.enums.DocumentFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DocumentProcessorRegistry {

    private final Map<DocumentFormat, DocumentProcessor> processors;

    public DocumentProcessorRegistry(List<DocumentProcessor> processorsList) {
        this.processors = processorsList.stream()
                .collect(Collectors.toMap(
                        DocumentProcessor::supports,
                        p -> p
                ));
    }

    public DocumentProcessor get(DocumentFormat format) {
        return processors.get(format);
    }
}