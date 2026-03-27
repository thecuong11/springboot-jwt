package com.example.springboot.controller;

import com.example.springboot.dto.LoginRequest;
import com.example.springboot.dto.LoginResponse;
import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.entity.RefreshToken;
import com.example.springboot.service.RefreshTokenService;
import com.example.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("User register successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        String accessToken = userService.login(request);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.username());
        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refreshToken");

        RefreshToken refreshToken = refreshTokenService.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Refresh token does not exist"));

        refreshTokenService.verifyExpiration(refreshToken);

        String newAccessToken = userService.generateTokenFromUsername(refreshToken.getUser().getUsername());

        return ResponseEntity.ok(new LoginResponse(newAccessToken, requestToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader){
        userService.logout(authHeader);
        return ResponseEntity.noContent().build();
    }
}
