package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document_headers")
@Getter
@Setter
@NoArgsConstructor
public class DocumentHeader  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String type;

    private String format;

    @OneToOne(mappedBy = "header")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Organization sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Organization receiver;
}