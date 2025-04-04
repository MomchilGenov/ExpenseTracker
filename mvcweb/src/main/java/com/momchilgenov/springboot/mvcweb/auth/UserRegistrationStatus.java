package com.momchilgenov.springboot.mvcweb.auth;


import com.momchilgenov.springboot.mvcweb.token.dto.JwtTokenPair;

public record UserRegistrationStatus(String usernameCollides, JwtTokenPair tokenPair) {
}
