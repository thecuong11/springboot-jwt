package com.example.springboot.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
    public ResponseEntity<?> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }
}
