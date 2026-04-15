package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.OrganizationResponseDTO;
import org.example.dto.OrganizationUpdateRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.entity.Organization;
import org.example.service.OrganizationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService service;

    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrganizationResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OrganizationResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public OrganizationResponseDTO create(@RequestBody @Valid Organization org) {
        return service.create(org);
    }

    @PutMapping("/{id}")
    public OrganizationResponseDTO update(@PathVariable Long id,
                                          @RequestBody @Valid OrganizationUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}