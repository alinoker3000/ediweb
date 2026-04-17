package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrganizationResponseDTO;
import org.example.dto.OrganizationUpdateRequestDTO;
import org.example.entity.Organization;
import org.example.mapper.OrganizationMapper;
import org.example.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repo;
    private final OrganizationMapper mapper;

    public List<OrganizationResponseDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrganizationResponseDTO findById(Long id) {
        return repo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    public OrganizationResponseDTO create(Organization req) {

        Organization org = new Organization();
        org.setName(req.getName());
        org.setGln(req.getGln());

        return mapper.toResponse(repo.save(org));
    }

    public OrganizationResponseDTO update(Long id, OrganizationUpdateRequestDTO req) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (req.getName() != null) {
            if (req.getName().isBlank()) {
                throw new IllegalArgumentException("name cannot be empty");
            }
            org.setName(req.getName());
        }

        if (req.getGln() != null) {
            if (req.getGln().isBlank()) {
                throw new IllegalArgumentException("gln cannot be empty");
            }
            if (!req.getGln().matches("\\d{13}")) {
                throw new IllegalArgumentException("gln must be 13 digits");
            }
            org.setGln(req.getGln());
        }

        return mapper.toResponse(repo.save(org));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Organization not found");
        }
        repo.deleteById(id);
    }
}