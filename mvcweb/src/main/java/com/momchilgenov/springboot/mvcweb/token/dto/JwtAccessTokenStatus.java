package com.momchilgenov.springboot.mvcweb.token.dto;


import com.momchilgenov.springboot.mvcweb.token.JwtClaimValidationStatus;

/**
 * @param iss             - issuer, either JwtClaimValidationStatus.VALID or JwtClaimValidationStatus.INVALID
 * @param aud             - issuer, either JwtClaimValidationStatus.VALID or JwtClaimValidationStatus.INVALID
 * @param exp             - true if expired, false otherwise
 * @param sub             - JwtClaimValidationStatus.EXISTS if everything is ok, JwtClaimValidationStatus.BANNED if user is banned,
 *                        JwtClaimValidationStatus.NOT_FOUND if no such user exists
 * @param sub_roles_match - true if user exists and roles in token match given user's roles (true for BANNED users too),
 *                        so always check sub claim status too.
 */
public record JwtAccessTokenStatus(JwtClaimValidationStatus iss, JwtClaimValidationStatus aud,
                                   boolean exp, JwtClaimValidationStatus sub, boolean sub_roles_match) {
}
