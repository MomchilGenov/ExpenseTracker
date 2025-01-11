package com.momchilgenov.springboot.mvcweb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthFilter(JwtUtil util) {
        this.jwtUtil = util;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = jwtUtil.extractJwtFromCookie(request);

        if (jwt != null && jwtUtil.validateToken(jwt)) {
            String username = jwtUtil.getUsernameFromToken(jwt);

            // Create an authentication token to access in custom authentication provider
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, jwtUtil.getRolesFromToken(jwt));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}

