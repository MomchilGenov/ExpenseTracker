package com.momchilgenov.springboot.mvcweb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
        System.out.println("in JwtAuthFilter");
        String jwt = jwtUtil.extractJwtFromAuthorizationHeader(request);
        if (jwt == null) {
            System.out.println("No jwt in authorization header");
            jwt = jwtUtil.extractJwtFromCookies(request);
            if (jwt == null) {
                System.out.println("Neither in cookies");
            }
        }


        if (jwt != null) {//&& jwtUtil.validateToken(jwt)) {
            System.out.println("received a jwt in filter");
            String username = jwtUtil.getUsernameFromToken(jwt);

            // Create an authentication token to access in custom authentication provider
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    username, null, jwtUtil.getRolesFromToken(jwt));
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //System.out.println("Fake authenticated");
        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("IvanTest", null, null));
        filterChain.doFilter(request, response);
    }
}

