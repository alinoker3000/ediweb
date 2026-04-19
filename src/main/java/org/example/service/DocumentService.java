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

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;
    private final OrganizationRepository orgRepo;
    private final DocumentMapper mapper;
    private final DocumentHeaderMapper headerMapper;
    private final CurrentUserService currentUser;

    private boolean belongsToUserOrg(Document doc) {
        Long orgId = currentUser.companyId();

        return doc.getHeader().getSender().getId().equals(orgId)
                || doc.getHeader().getReceiver().getId().equals(orgId);
    }

    public List<DocumentResponseDTO> findAll() {

        if (currentUser.isAdmin()) {
            return documentRepo.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        return documentRepo.findAll()
                .stream()
                .filter(this::belongsToUserOrg)
                .map(mapper::toResponse)
                .toList();
    }

    public DocumentResponseDTO findById(Long id) {

        Document doc = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!currentUser.isAdmin() && !belongsToUserOrg(doc)) {
            throw new RuntimeException("Access denied");
        }

        return mapper.toResponse(doc);
    }

    public DocumentResponseDTO create(DocumentCreateRequestDTO req) {

        Organization sender = orgRepo.findById(req.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Organization receiver = orgRepo.findById(req.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (!currentUser.isAdmin()) {

            Long myOrg = currentUser.companyId();

            if (!sender.getId().equals(myOrg) && !receiver.getId().equals(myOrg)) {
                throw new RuntimeException("Access denied");
            }
        }

        Document document = mapper.toEntity(req);

        DocumentHeader header = headerMapper.toEntity(req);
        header.setSender(sender);
        header.setReceiver(receiver);

        document.setHeader(header);

        return mapper.toResponse(documentRepo.save(document));
    }

    public DocumentResponseDTO update(Long id, DocumentUpdateRequestDTO req) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!currentUser.isAdmin() && !belongsToUserOrg(document)) {
            throw new RuntimeException("Access denied");
        }

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

    public void delete(Long id) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!currentUser.isAdmin() && !belongsToUserOrg(document)) {
            throw new RuntimeException("Access denied");
        }

        documentRepo.delete(document);
    }
}