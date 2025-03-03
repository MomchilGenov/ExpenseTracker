package com.momchilgenov.springboot.servicecore.token;

public record JwtTokenPair(JwtAccessToken accessToken, JwtRefreshToken refreshToken) {
}
