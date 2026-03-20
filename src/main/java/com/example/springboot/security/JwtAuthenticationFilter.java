package com.example.springboot.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;


//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        String header = req.getHeader("Authorization");
//
//        if (header != null && header.startsWith("Bearer ")){
//            String token = header.substring(7);
//
//            if (jwtUtils.validateToken(token)){
//                String username = jwtUtils.getUsernameFromToken(token);
//
//                SecurityContextHolder.getContext().setAuthentication(
//                        new UsernamePasswordAuthenticationToken(username, null, null)
//                );
//            }
//        }
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        final String authHeader = req.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        String token =  authHeader.substring(7);
        try{
            Claims claims = jwtUtils.getClaims(token);
            String username = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);

            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception e) {

        }

        filterChain.doFilter(req,response);
    }
}
