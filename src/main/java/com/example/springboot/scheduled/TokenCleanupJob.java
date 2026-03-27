package com.example.springboot.scheduled;

import com.example.springboot.repository.InvalidatedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TokenCleanupJob {

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 3 * * *")
    public void cleanup(){
        invalidatedTokenRepository.deleteAllByExpiryTimeBefore(Instant.now());
    }
}
