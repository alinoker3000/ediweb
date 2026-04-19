package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentResponseDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.service.DocumentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @GetMapping
    public List<DocumentResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@documentAccessPolicy.canRead(#id)")
    public DocumentResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("@documentAccessPolicy.canCreate(#req.senderId, #req.receiverId)")
    public DocumentResponseDTO create(@RequestBody DocumentCreateRequestDTO req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@documentAccessPolicy.canUpdate(#id)")
    public DocumentResponseDTO update(@PathVariable Long id,
                                      @RequestBody DocumentUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@documentAccessPolicy.canDelete(#id)")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}