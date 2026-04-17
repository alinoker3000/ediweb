package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreateRequestDTO;
import org.example.dto.UserResponseDTO;
import org.example.dto.UserUpdateRequestDTO;
import org.example.entity.Organization;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.OrganizationRepository;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final OrganizationRepository orgRepo;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> findAll() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO findById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserResponseDTO create(UserCreateRequestDTO req) {

        Organization org = orgRepo.findById(req.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setLogin(req.getLogin());
        user.setAdmin(req.isAdmin());
        user.setOrganization(org);

        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return userMapper.toResponse(userRepo.save(user));
    }

    public UserResponseDTO update(Long id, UserUpdateRequestDTO req) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (req.getFirstName() != null) {
            user.setFirstName(req.getFirstName());
        }

        if (req.getLastName() != null) {
            user.setLastName(req.getLastName());
        }

        if (req.getLogin() != null) {
            user.setLogin(req.getLogin());
        }

        if (req.getAdmin() != null) {
            user.setAdmin(req.getAdmin());
        }

        if (req.getOrganizationId() != null) {
            Organization org = orgRepo.findById(req.getOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Organization not found"));
            user.setOrganization(org);
        }

        if (req.getPassword() != null) {
            if (req.getPassword().isBlank()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        return userMapper.toResponse(userRepo.save(user));
    }

    public void delete(Long id) {
        userRepo.deleteById(id);
    }
}