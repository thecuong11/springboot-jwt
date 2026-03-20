package com.example.springboot.service;

import com.example.springboot.dto.LoginRequest;
import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.dto.UserDTO;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.exception.NotFoundException;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.security.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public void register(RegisterRequest request){
        if (userRepository.findByUsername(request.username()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        Set<Role> userRoles = new HashSet<>();

        if (request.roles() != null){
            request.roles().forEach(roleName -> {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                userRoles.add(role);
            });
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

//        Set<String> roles = user.getRoles().stream()
//                .map(Role::getName)
//                .collect(Collectors.toSet());
        Set<String> roles = user.getRoles() == null ? new HashSet<>() :
                user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

        return jwtUtils.generateToken(user.getUsername(), roles);
    }
}
