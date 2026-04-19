package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserCreateRequestDTO;
import org.example.dto.user.UserResponseDTO;
import org.example.dto.user.UserUpdateRequestDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.example.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userAccessPolicy.canRead(#id)")
    public UserResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @PreAuthorize("@userAccessPolicy.canCreate(#req.organizationId)")
    public UserResponseDTO create(@RequestBody UserCreateRequestDTO req) {
        return service.create(req);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@userAccessPolicy.canUpdate(#id)")
    public UserResponseDTO update(@PathVariable Long id,
                                  @RequestBody UserUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userAccessPolicy.canDelete(#id)")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}