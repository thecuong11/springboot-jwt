package com.example.springboot.repository;

import com.example.springboot.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsById(String id);

    void deleteAllByExpiryTimeBefore(Instant now);
}
