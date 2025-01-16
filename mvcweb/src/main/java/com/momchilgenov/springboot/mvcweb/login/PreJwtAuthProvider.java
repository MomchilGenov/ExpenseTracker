package com.momchilgenov.springboot.mvcweb.login;

import com.momchilgenov.springboot.mvcweb.security.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PreJwtAuthProvider implements AuthenticationProvider {

    private final BackendService backendService;
    private final JwtUtil jwtUtil;

    @Autowired
    public PreJwtAuthProvider(BackendService backendService, JwtUtil jwtUtil) {
        this.backendService = backendService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        System.out.println("in prejwtauthprovider class");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.out.println("My custom authentication provider received username=" + username + " and password=" + password);
        // Send credentials to backend service to validate and generate JWT
        String jwtToken = backendService.authenticateUser(username, password);
        //authentication.setAuthenticated(true);


        SecurityContextHolder.getContext().setAuthentication(authentication);


        return new UsernamePasswordAuthenticationToken(username, null, null);
        /*
        if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
            // On successful authentication, you can return an Authentication token
            // along with roles granted from the JWT.
            String roles = jwtUtil.getRolesFromToken(jwtToken).toString(); // Assuming roles are part of JWT

            return new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roles)) // Add roles to granted authorities
            );
        } else {
            throw new RuntimeException("Authentication failed - Invalid username or password");
        }
        */

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


