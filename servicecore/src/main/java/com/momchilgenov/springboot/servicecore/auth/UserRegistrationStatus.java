package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.token.JwtTokenPair;

public record UserRegistrationStatus(boolean usernameCollides, JwtTokenPair tokenPair) {
}
