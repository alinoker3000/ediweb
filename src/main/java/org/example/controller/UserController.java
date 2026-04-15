package org.example.controller;

import org.example.dto.UserCreateRequestDTO;
import org.example.dto.UserResponseDTO;
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
    public UserResponseDTO create(@RequestBody UserCreateRequestDTO user) {
        return service.create(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}