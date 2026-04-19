package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.organization.OrganizationCreateRequestDTO;
import org.example.dto.organization.OrganizationUpdateRequestDTO;
import org.example.dto.organization.OrganizationResponseDTO;
import org.example.entity.Organization;
import org.example.mapper.OrganizationMapper;
import org.example.repository.OrganizationRepository;
import org.example.security.AuthUser;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repo;
    private final OrganizationMapper mapper;
    private final CurrentUserService currentUser;

    @Transactional(readOnly = true)
    public List<OrganizationResponseDTO> findAll() {

        AuthUser user = currentUser.get();

        if (user.isAdmin()) {
            return repo.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        return repo.findById(user.getOrganizationId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrganizationResponseDTO findById(Long id) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        return mapper.toResponse(org);
    }

    @Transactional
    public OrganizationResponseDTO create(OrganizationCreateRequestDTO req) {
        Organization org = mapper.toEntity(req);
        return mapper.toResponse(repo.save(org));
    }

    @Transactional
    public OrganizationResponseDTO update(Long id, OrganizationUpdateRequestDTO req) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        mapper.update(req, org);

        return mapper.toResponse(repo.save(org));
    }

    @Transactional
    public void delete(Long id) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        repo.delete(org);
    }
}