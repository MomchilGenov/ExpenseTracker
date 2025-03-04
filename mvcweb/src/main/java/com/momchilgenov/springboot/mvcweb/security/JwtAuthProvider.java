package com.momchilgenov.springboot.mvcweb.security;

import com.momchilgenov.springboot.mvcweb.auth.AuthenticationService;
import com.momchilgenov.springboot.mvcweb.token.JwtAuthenticationToken;
import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
        System.out.println("in prejwtauthprovider class");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        System.out.println("My custom authentication provider received username=" + username + " and password=" + password);
        // Send credentials to backend service to validate and generate JWT
        JwtTokenPair jwtTokenPair = authenticationService.authenticateUser(username, password);
        //authentication.setAuthenticated(true);


        SecurityContextHolder.getContext().setAuthentication(authentication);

        //fixme (authorities)
        return new JwtAuthenticationToken(username, null, null, jwtTokenPair);
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


