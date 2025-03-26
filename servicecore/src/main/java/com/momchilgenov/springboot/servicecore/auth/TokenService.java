package com.momchilgenov.springboot.servicecore.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

    /**
     * revokedTokens - username,iat timestamp
     * any token for a user with an iat claim prior to the Date value in the map is considered revoked
     */
    private final ConcurrentHashMap<String, Date> revokedTokens;

    @Autowired
    public TokenService() {
        this.revokedTokens = new ConcurrentHashMap<>();
    }

    /**
     * revokes all tokens for a given user issued before the present moment
     *
     * @param username - the user for whom all tokens issued up to current moment should be revoked
     */
    public void revokeAll(String username) {
        revokedTokens.put(username, Date.from(Instant.now()));
    }

    /**
     * @param username - the username of the user
     * @param iatClaim - the token claim to verify against to see if the token from
     *                 which the claim was extracted is revoked
     * @return - true, if the token is revoked, false otherwise
     */
    public boolean isRevoked(String username, Date iatClaim) {
        return this.revokedTokens.get(username).after(iatClaim);
    }

}
