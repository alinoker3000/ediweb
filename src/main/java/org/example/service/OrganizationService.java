package org.example.service;

import org.example.entity.Organization;
import org.example.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository repo;

    public OrganizationService(OrganizationRepository repo) {
        this.repo = repo;
    }

    public List<Organization> findAll() {
        return repo.findAll();
    }

    public Organization create(Organization org) {
        return repo.save(org);
    }
}