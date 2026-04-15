package org.example.controller;

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
    public List<Organization> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Organization create(@RequestBody Organization org) {
        return service.create(org);
    }
}