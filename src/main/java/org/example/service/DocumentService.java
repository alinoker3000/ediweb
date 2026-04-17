package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.DocumentCreateRequestDTO;
import org.example.dto.DocumentResponseDTO;
import org.example.dto.DocumentUpdateRequestDTO;
import org.example.entity.Document;
import org.example.entity.DocumentHeader;
import org.example.entity.Organization;
import org.example.mapper.DocumentMapper;
import org.example.repository.DocumentRepository;
import org.example.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;
    private final OrganizationRepository orgRepo;
    private final DocumentMapper mapper;

    public List<DocumentResponseDTO> findAll() {
        return documentRepo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public DocumentResponseDTO findById(Long id) {
        return documentRepo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public DocumentResponseDTO create(DocumentCreateRequestDTO req) {

        Organization sender = orgRepo.findById(req.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Organization receiver = orgRepo.findById(req.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        DocumentHeader header = new DocumentHeader();
        header.setNumber(req.getNumber());
        header.setType(req.getType());
        header.setFormat(req.getFormat());
        header.setSender(sender);
        header.setReceiver(receiver);

        Document document = new Document();
        document.setHeader(header);
        document.setData(req.getData());

        return mapper.toResponse(documentRepo.save(document));
    }

    public DocumentResponseDTO update(Long id, DocumentUpdateRequestDTO req) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        DocumentHeader header = document.getHeader();

        if (req.getNumber() != null) {
            header.setNumber(req.getNumber());
        }

        if (req.getType() != null) {
            header.setType(req.getType());
        }

        if (req.getFormat() != null) {
            header.setFormat(req.getFormat());
        }

        if (req.getSenderId() != null) {
            Organization sender = orgRepo.findById(req.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found"));
            header.setSender(sender);
        }

        if (req.getReceiverId() != null) {
            Organization receiver = orgRepo.findById(req.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("Receiver not found"));
            header.setReceiver(receiver);
        }

        if (req.getData() != null) {
            document.setData(req.getData());
        }

        return mapper.toResponse(documentRepo.save(document));
    }

    public void delete(Long id) {
        if (!documentRepo.existsById(id)) {
            throw new RuntimeException("Document not found");
        }
        documentRepo.deleteById(id);
    }
}
