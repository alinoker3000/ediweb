package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserCreateRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.dto.UserUpdateRequestDTO;
import org.springframework.web.bind.annotation.*;
import org.example.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponseDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDTO get(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody @Valid UserCreateRequestDTO req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @RequestBody @Valid UserUpdateRequestDTO req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}