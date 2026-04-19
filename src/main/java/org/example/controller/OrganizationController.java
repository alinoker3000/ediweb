package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.organization.OrganizationCreateRequestDTO;
import org.example.dto.organization.OrganizationUpdateRequestDTO;
import org.example.dto.organization.OrganizationResponseDTO;
import org.example.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService service;

    @GetMapping
    public List<OrganizationResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrganizationResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public OrganizationResponseDTO create(@RequestBody @Valid OrganizationCreateRequestDTO dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    public OrganizationResponseDTO patch(@PathVariable Long id,
                                         @RequestBody @Valid OrganizationUpdateRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}