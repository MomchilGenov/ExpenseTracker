package com.momchilgenov.springboot.servicecore.auth;

public record JwtAccessTokenStatus(JwtClaimValidationStatus iss, JwtClaimValidationStatus aud,
                                   boolean exp, JwtClaimValidationStatus sub, boolean sub_roles_match) {
}
