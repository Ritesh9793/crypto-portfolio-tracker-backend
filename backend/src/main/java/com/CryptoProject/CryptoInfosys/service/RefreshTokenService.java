package com.CryptoProject.CryptoInfosys.service;

import com.CryptoProject.CryptoInfosys.model.RefreshToken;
import com.CryptoProject.CryptoInfosys.model.User;
import com.CryptoProject.CryptoInfosys.repository.RefreshTokenRepository;
import com.CryptoProject.CryptoInfosys.security.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    public String issueRefreshToken(User user) {
        String token = jwtUtils.generateRefreshToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseGet(RefreshToken::new);
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public User validateAndGetUser(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now()) || !jwtUtils.isRefreshToken(token)) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        return refreshToken.getUser();
    }
}
