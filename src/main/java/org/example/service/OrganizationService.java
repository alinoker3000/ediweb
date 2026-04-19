package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.organization.OrganizationCreateRequestDTO;
import org.example.dto.organization.OrganizationUpdateRequestDTO;
import org.example.dto.organization.OrganizationResponseDTO;
import org.example.entity.Organization;
import org.example.mapper.OrganizationMapper;
import org.example.repository.OrganizationRepository;
import org.example.security.CurrentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repo;
    private final OrganizationMapper mapper;
    private final CurrentUserService currentUser;

    public List<OrganizationResponseDTO> findAll() {

        if (currentUser.isAdmin()) {
            return repo.findAll()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        return repo.findById(currentUser.companyId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OrganizationResponseDTO findById(Long id) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (!currentUser.isAdmin()) {
            if (!org.getId().equals(currentUser.companyId())) {
                throw new RuntimeException("Access denied");
            }
        }

        return mapper.toResponse(org);
    }

    public OrganizationResponseDTO create(OrganizationCreateRequestDTO req) {

        if (!currentUser.isAdmin()) {
            throw new RuntimeException("Only admin can create organizations");
        }

        Organization org = mapper.toEntity(req);
        return mapper.toResponse(repo.save(org));
    }

    public OrganizationResponseDTO update(Long id, OrganizationUpdateRequestDTO req) {

        Organization org = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        if (!currentUser.isAdmin()) {

            if (!org.getId().equals(currentUser.companyId())) {
                throw new RuntimeException("Access denied");
            }
        }

        mapper.update(req, org);

        return mapper.toResponse(repo.save(org));
    }

    public void delete(Long id) {

        if (!currentUser.isAdmin()) {
            throw new RuntimeException("Only admin can delete organizations");
        }

        repo.deleteById(id);
    }
}