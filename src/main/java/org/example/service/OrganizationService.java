package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.OrganizationCreateRequestDTO;
import org.example.dto.OrganizationUpdateRequestDTO;
import org.example.dto.OrganizationResponseDTO;
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

    public OrganizationResponseDTO create(OrganizationCreateRequestDTO req) {
        Organization org = mapper.toEntity(req);
        return mapper.toResponse(repo.save(org));
    }

    public OrganizationResponseDTO update(Long id, OrganizationUpdateRequestDTO req) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.update(req, org);

        return mapper.toResponse(repo.save(org));
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Organization not found");
        }
        repo.deleteById(id);
    }
}