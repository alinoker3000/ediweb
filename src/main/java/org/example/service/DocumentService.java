package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.dto.document.DocumentResponseDTO;
import org.example.entity.Document;
import org.example.entity.DocumentHeader;
import org.example.entity.Organization;
import org.example.mapper.DocumentHeaderMapper;
import org.example.mapper.DocumentMapper;
import org.example.repository.DocumentRepository;
import org.example.repository.OrganizationRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;
    private final OrganizationRepository orgRepo;
    private final DocumentMapper mapper;
    private final DocumentHeaderMapper headerMapper;
    private final CurrentUserService currentUser;

    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> findAll() {
        return documentRepo
                .findAccessibleDocument(
                        currentUser.companyId(),
                        currentUser.isAdmin())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DocumentResponseDTO findById(Long id) {

        Document doc = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return mapper.toResponse(doc);
    }

    @Transactional
    public DocumentResponseDTO create(DocumentCreateRequestDTO req) {

        Organization sender = orgRepo.findById(req.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Organization receiver = orgRepo.findById(req.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Document document = mapper.toEntity(req);

        DocumentHeader header = headerMapper.toEntity(req);
        header.setSender(sender);
        header.setReceiver(receiver);

        document.setHeader(header);

        return mapper.toResponse(documentRepo.save(document));
    }

    @Transactional
    public DocumentResponseDTO update(Long id, DocumentUpdateRequestDTO req) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        mapper.update(req, document);

        DocumentHeader header = document.getHeader();
        if (header == null) {
            header = new DocumentHeader();
            document.setHeader(header);
        }

        headerMapper.update(req, header);

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

        return mapper.toResponse(documentRepo.save(document));
    }

    @Transactional
    public void delete(Long id) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        documentRepo.delete(document);
    }
}