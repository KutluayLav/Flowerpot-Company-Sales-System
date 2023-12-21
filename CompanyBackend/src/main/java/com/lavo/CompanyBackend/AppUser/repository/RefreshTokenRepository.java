package com.lavo.CompanyBackend.AppUser.repository;

import com.lavo.CompanyBackend.AppUser.model.RefreshToken;
import com.lavo.CompanyBackend.AppUser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);
}
