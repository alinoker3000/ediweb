package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.DocumentFormat;

@Entity
@Table(name = "document_types")
@Getter
@Setter
@NoArgsConstructor
public class DocumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Enumerated(EnumType.STRING)
    private DocumentFormat format;

    @Column(name = "xsd_schema", columnDefinition = "TEXT")
    private String xsdSchema;
}