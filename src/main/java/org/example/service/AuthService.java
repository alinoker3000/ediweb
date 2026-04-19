package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.auth.AuthResponseDTO;
import org.example.dto.auth.LoginRequestDTO;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO login(LoginRequestDTO req) {

        User user = userRepo.findByLogin(req.getLogin())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getOrganization().getId(),
                user.isAdmin()
        );
    }
}