package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import com.momchilgenov.springboot.servicecore.token.JwtAccessToken;
import com.momchilgenov.springboot.servicecore.token.JwtRefreshToken;
import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;

public class AuthService {

    public JwtTokenPair authenticateUser(User user) {
        //todo - implement
        JwtAccessToken accessToken = new JwtAccessToken("Dummy access token"); //todo - implement generation with helper class
        JwtRefreshToken refreshToken = new JwtRefreshToken("Dummy refresh token"); //todo - implement generation with helper class
        JwtTokenPair jwtTokenPair = new JwtTokenPair(accessToken, refreshToken);
        return jwtTokenPair;
    }
}
