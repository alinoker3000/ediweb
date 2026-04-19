package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.UserCreateRequestDTO;
import org.example.dto.user.UserResponseDTO;
import org.example.dto.user.UserUpdateRequestDTO;
import org.example.entity.Organization;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.repository.OrganizationRepository;
import org.example.repository.UserRepository;
import org.example.security.AuthUser;
import org.example.security.CurrentUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final OrganizationRepository orgRepo;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUser;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {

        AuthUser user = currentUser.get();

        if (user.isAdmin()) {
            return userRepo.findAll()
                    .stream()
                    .map(userMapper::toResponse)
                    .toList();
        }

        return userRepo.findByOrganizationId(user.getOrganizationId())
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponse(user);
    }

    @Transactional
    public UserResponseDTO create(UserCreateRequestDTO req) {

        Organization org = orgRepo.findById(req.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        User user = userMapper.toEntity(req);

        user.setOrganization(org);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateRequestDTO req) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.update(req, user);

        if (req.getOrganizationId() != null) {
            Organization org = orgRepo.findById(req.getOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Organization not found"));
            user.setOrganization(org);
        }

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        return userMapper.toResponse(userRepo.save(user));
    }

    @Transactional
    public void delete(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepo.delete(user);
    }
}