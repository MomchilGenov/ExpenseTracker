package com.momchilgenov.springboot.mvcweb.token.dto;

public record JwtTokenPair(JwtAccessToken accessToken, JwtRefreshToken refreshToken) {
}
