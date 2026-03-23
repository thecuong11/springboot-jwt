package com.example.springboot.exception.handler;

import com.example.springboot.exception.custom.NotFoundException;
import com.example.springboot.exception.custom.RoleNotFoundException;
import com.example.springboot.exception.custom.UsernameAlreadyExistsException;
import com.example.springboot.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<?> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(404).body(exception.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExists(UsernameAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), 400));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFound(RoleNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), 404));
    }
}
