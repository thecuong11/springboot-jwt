package com.example.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/v1/api")
public class MyController {

    @GetMapping("/test-user")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> userAccess() {
        return ResponseEntity.ok(Collections.singletonMap("message", "ok-user"));
    }

    @GetMapping("/test-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> adminAccess() {
        return ResponseEntity.ok(Collections.singletonMap("message", "ok-admin"));
    }

}
