package com.example.springboot.service;

import com.example.springboot.dto.LoginRequest;
import com.example.springboot.dto.RegisterRequest;
import com.example.springboot.entity.InvalidatedToken;
import com.example.springboot.entity.Role;
import com.example.springboot.entity.User;
import com.example.springboot.exception.custom.RoleNotFoundException;
import com.example.springboot.exception.custom.UsernameAlreadyExistsException;
import com.example.springboot.repository.InvalidatedTokenRepository;
import com.example.springboot.repository.RoleRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    public void register(RegisterRequest request){
        if (userRepository.findByUsername(request.username()).isPresent()){
            throw new UsernameAlreadyExistsException(request.username());
        }

        Set<Role> userRoles = new HashSet<>();

        if (request.roles() != null){
            request.roles().forEach(roleName -> {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException(roleName));
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

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String username = auth.getName();
        Set<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return jwtUtils.generateToken(username, roles);
    }

    public String generateTokenFromUsername(String username){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return jwtUtils.generateToken(username, roles);
    }

    public void logout(String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("Invalid token");
        }

        String token = authHeader.substring(7);
        String jti = jwtUtils.extractJti(token);
        Instant expiry = jwtUtils.extractExpiry(token);

        invalidatedTokenRepository.save(new InvalidatedToken(jti, expiry));
    }
}
