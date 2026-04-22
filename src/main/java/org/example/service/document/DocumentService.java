package org.example.service.document;

import lombok.RequiredArgsConstructor;
import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.dto.document.DocumentResponseDTO;
import org.example.entity.Document;
import org.example.entity.DocumentHeader;
import org.example.entity.DocumentType;
import org.example.entity.Organization;
import org.example.mapper.DocumentMapper;
import org.example.repository.DocumentRepository;
import org.example.repository.DocumentTypeRepository;
import org.example.repository.OrganizationRepository;
import org.example.security.AuthUser;
import org.example.security.CurrentUserService;
import org.example.service.document.processor.DocumentProcessor;
import org.example.service.document.processor.DocumentProcessorRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;
    private final OrganizationRepository orgRepo;
    private final DocumentMapper mapper;
    private final CurrentUserService currentUser;
    private final DocumentTypeRepository typeRepo;
    private final DocumentProcessorRegistry processorRegistry;

    private void validate(DocumentType type, String data) {
        DocumentProcessor processor = processorRegistry.get(type.getFormat());

        if (processor == null) {
            throw new RuntimeException("Unsupported format: " + type.getFormat());
        }

        processor.validate(data, type);
    }

    @Transactional(readOnly = true)
    public List<DocumentResponseDTO> findAll() {

        AuthUser user = currentUser.get();

        if (user.isAdmin()) {
            return documentRepo.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        return documentRepo.findByOrganizationAccess(user.getOrganizationId())
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

    private DocumentHeader buildHeader(DocumentCreateRequestDTO req,
                                       Organization sender,
                                       Organization receiver,
                                       DocumentType type) {

        DocumentHeader header = new DocumentHeader();
        header.setNumber(req.getNumber());
        header.setSender(sender);
        header.setReceiver(receiver);
        header.setType(type);

        return header;
    }

    @Transactional
    public DocumentResponseDTO create(DocumentCreateRequestDTO req) {

        Organization sender = orgRepo.findById(req.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        Organization receiver = orgRepo.findById(req.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        DocumentType type = typeRepo.findByCode(req.getType())
                .orElseThrow(() -> new RuntimeException("Unknown document type"));

        DocumentProcessor processor = processorRegistry.get(type.getFormat());

        if (processor == null) {
            throw new RuntimeException("Unsupported format: " + type.getFormat());
        }

        String xml = processor.generate(type, req.getPayload());

        processor.validate(xml, type);

        DocumentHeader header = new DocumentHeader();
        header.setNumber(req.getNumber());
        header.setSender(sender);
        header.setReceiver(receiver);
        header.setType(type);

        Document document = new Document();
        document.setHeader(header);
        document.setData(xml);

        return mapper.toResponse(documentRepo.save(document));
    }

    @Transactional
    public DocumentResponseDTO update(Long id, DocumentUpdateRequestDTO req) {

        Document document = documentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        DocumentHeader header = document.getHeader();
        if (header == null) {
            throw new RuntimeException("Document header not found");
        }

        if (req.getNumber() != null) {
            header.setNumber(req.getNumber());
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

        if (req.getType() != null) {
            DocumentType type = typeRepo.findByCode(req.getType())
                    .orElseThrow(() -> new RuntimeException("Unknown document type"));

            header.setType(type);
        }

        if (req.getPayload() != null) {

            DocumentType type = header.getType();

            if (type == null) {
                throw new RuntimeException("Document type must be set before payload update");
            }

            DocumentProcessor processor = processorRegistry.get(type.getFormat());

            String xml = processor.generate(type, req.getPayload());

            processor.validate(xml, type);

            document.setData(xml);
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