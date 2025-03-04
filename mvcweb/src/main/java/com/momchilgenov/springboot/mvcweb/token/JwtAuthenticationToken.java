package com.momchilgenov.springboot.mvcweb.token;

import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final JwtTokenPair tokenPair;

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
                                  JwtTokenPair tokenPair) {
        super(principal, credentials, authorities);
        this.tokenPair = tokenPair;
    }

    public JwtTokenPair getTokenPair() {
        return this.tokenPair;
    }
}
