package com.lavo.CompanyBackend.AppUser.service;

import com.lavo.CompanyBackend.AppUser.model.RefreshToken;
import com.lavo.CompanyBackend.AppUser.model.User;
import com.lavo.CompanyBackend.AppUser.repository.RefreshTokenRepository;
import com.lavo.CompanyBackend.AppUser.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);

            if (existingToken.isPresent()) {
                RefreshToken refreshToken = existingToken.get();
                refreshToken.setExpiryDate(Instant.now().plusMillis(600000));
                return refreshTokenRepository.save(refreshToken);
            } else {
                RefreshToken refreshToken = RefreshToken.builder()
                        .user(user)
                        .token(UUID.randomUUID().toString())
                        .expiryDate(Instant.now().plusMillis(600000))
                        .build();

                return refreshTokenRepository.save(refreshToken);
            }
        } else {
            throw new UsernameNotFoundException("User Not Found with Email " + email);
        }
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

}
