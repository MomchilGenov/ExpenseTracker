package com.momchilgenov.dbcore.dao.dto;

public record UserAuthenticationStatus(UserCredentialsStatus usernameExists, UserCredentialsStatus passwordMatches,
                                       UserCredentialsStatus rolesMatch) {
}
