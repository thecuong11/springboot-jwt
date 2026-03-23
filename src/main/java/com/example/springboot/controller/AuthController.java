package com.example.springboot.controller;

import com.example.springboot.dto.LoginRequest;
import com.example.springboot.dto.LoginResponse;
import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.ok("User register successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        String token = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
