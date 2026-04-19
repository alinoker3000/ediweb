package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.document.DocumentCreateRequestDTO;
import org.example.dto.document.DocumentResponseDTO;
import org.example.dto.document.DocumentUpdateRequestDTO;
import org.example.service.DocumentService;
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
    public DocumentResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public DocumentResponseDTO create(@RequestBody @Valid DocumentCreateRequestDTO req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    public DocumentResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody DocumentUpdateRequestDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}