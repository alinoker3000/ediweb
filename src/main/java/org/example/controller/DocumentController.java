package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DocumentCreateRequestDTO;
import org.example.dto.DocumentResponseDTO;
import org.example.dto.DocumentUpdateRequestDTO;
import org.example.mapper.DocumentMapper;
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

    @PutMapping("/{id}")
    public DocumentResponseDTO update(@PathVariable Long id,
                                      @RequestBody @Valid DocumentUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}