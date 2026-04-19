package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.organization.OrganizationCreateRequestDTO;
import org.example.dto.organization.OrganizationUpdateRequestDTO;
import org.example.dto.organization.OrganizationResponseDTO;
import org.example.service.OrganizationService;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@organizationAccessPolicy.canRead(#id)")
    public OrganizationResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("@organizationAccessPolicy.canCreate()")
    public OrganizationResponseDTO create(@RequestBody OrganizationCreateRequestDTO req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@organizationAccessPolicy.canUpdate(#id)")
    public OrganizationResponseDTO update(@PathVariable Long id,
                                          @RequestBody OrganizationUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@organizationAccessPolicy.canDelete(#id)")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}