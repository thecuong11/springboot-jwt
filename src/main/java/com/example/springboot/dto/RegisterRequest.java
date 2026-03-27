package com.example.springboot.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(

        @NotBlank(message = "Username must not be blank")
        String username,

        @NotBlank(message = "Password must not be blank")
        @Size(min = 6, max = 20, message = "Password must be at least 6 character")
        String password,

        @NotEmpty(message = "Roles must not be empty")
        Set<@NotBlank(message = "Roles must not be blank") String> roles
)
{ }
