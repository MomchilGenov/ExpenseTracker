package com.momchilgenov.springboot.mvcweb.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String JWT;

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,
                                  String jwt) {
        super(principal, credentials, authorities);
        this.JWT = jwt;
    }

    public String getJWT() {
        return this.JWT;
    }
}
