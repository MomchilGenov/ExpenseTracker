package com.momchilgenov.springboot.mvcweb.security;

import com.momchilgenov.springboot.mvcweb.auth.AuthenticationService;
import com.momchilgenov.springboot.mvcweb.token.JwtAuthenticationToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtAuthProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthProvider(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        System.out.println("Calling JwtAuthProvider class");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.out.println("My custom authentication provider received username=" + username + " and password=" + password);
        // Send credentials to backend service to validate credentials and generate JWT
        JwtTokenPair jwtTokenPair = authenticationService.authenticateUser(username, password);
        if (jwtTokenPair == null || jwtTokenPair.refreshToken().token() == null
                || jwtTokenPair.accessToken().token() == null) {
            return null;
        }
        List<GrantedAuthority> roles = jwtUtil.getRolesFromToken(jwtTokenPair.accessToken().token());

        return new JwtAuthenticationToken(username, null, roles, jwtTokenPair);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}


