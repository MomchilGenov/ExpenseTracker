package com.momchilgenov.springboot.mvcweb.security;

import com.momchilgenov.springboot.mvcweb.exception.ExpiredJwtTokenException;
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
        String jwt = jwtUtil.extractJwt(request);
        try {
            //todo instead of validating it here, you should call and api to the servicecore to
            //validate it just like for login and if it the response says it's valid proceed as now
            //otherwise initiate refresh mechanism if refreshtoken available, if not, request login
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                System.out.println("Received a jwt in filter");
                String username = jwtUtil.getUsernameFromToken(jwt);

                // Create an authentication token to access in custom authentication provider
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, jwtUtil.getRolesFromToken(jwt));
                //populates the authentication object with more details from the request object
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtTokenException e) {
            //todo - place here refresh token logic, if it fails then go to next catch statement
            System.out.println("JWT is expired!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your session has expired!Please login again.");
        } catch (Exception e) {
            System.out.println("JWT is invalid!");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token!Please login again.");
            throw new RuntimeException("this is the problem", e);

        }

    }
}

