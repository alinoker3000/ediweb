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
import org.example.security.CurrentUserService;
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
    private final CurrentUserService currentUser;

    private boolean sameOrg(User user) {
        return user.getOrganization().getId().equals(currentUser.companyId());
    }

    public List<UserResponseDTO> findAll() {

        if (currentUser.isAdmin()) {
            return userRepo.findAll()
                    .stream()
                    .map(userMapper::toResponse)
                    .toList();
        }

        return userRepo.findAll()
                .stream()
                .filter(this::sameOrg)
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO findById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (currentUser.isAdmin()) {
            return userMapper.toResponse(user);
        }

        if (!user.getOrganization().getId().equals(currentUser.companyId())) {
            throw new RuntimeException("Access denied");
        }

        return userMapper.toResponse(user);
    }

    public UserResponseDTO create(UserCreateRequestDTO req) {

        if (!currentUser.isAdmin()) {
            throw new RuntimeException("Only admin can create users");
        }

        Organization org = orgRepo.findById(currentUser.companyId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        User user = userMapper.toEntity(req);

        user.setOrganization(org);
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        return userMapper.toResponse(userRepo.save(user));
    }

    public UserResponseDTO update(Long id, UserUpdateRequestDTO req) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!currentUser.isAdmin()) {
            if (!user.getId().equals(currentUser.userId())) {
                throw new RuntimeException("Access denied");
            }
        }

        userMapper.update(req, user);

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        return userMapper.toResponse(userRepo.save(user));
    }

    public void delete(Long id) {

        if (!currentUser.isAdmin()) {
            throw new RuntimeException("Only admin can delete users");
        }

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOrganization().getId().equals(currentUser.companyId())) {
            throw new RuntimeException("Access denied");
        }

        userRepo.delete(user);
    }
}