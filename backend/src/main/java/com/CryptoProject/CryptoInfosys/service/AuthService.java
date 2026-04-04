package com.CryptoProject.CryptoInfosys.service;

import com.CryptoProject.CryptoInfosys.dto.AuthRequest;
import com.CryptoProject.CryptoInfosys.dto.AuthResponse;
import com.CryptoProject.CryptoInfosys.dto.RegisterRequest;
import com.CryptoProject.CryptoInfosys.model.User;
import com.CryptoProject.CryptoInfosys.repository.UserRepository;
import com.CryptoProject.CryptoInfosys.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepo,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            RefreshTokenService refreshTokenService
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        String refreshToken = refreshTokenService.issueRefreshToken(user);
        return new AuthResponse(accessToken, refreshToken, "Bearer", user.getEmail(), user.getName());
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        User user = refreshTokenService.validateAndGetUser(refreshToken);
        String accessToken = jwtUtils.generateAccessToken(user.getEmail());
        String rotatedRefreshToken = refreshTokenService.issueRefreshToken(user);
        return new AuthResponse(accessToken, rotatedRefreshToken, "Bearer", user.getEmail(), user.getName());
    }
}
