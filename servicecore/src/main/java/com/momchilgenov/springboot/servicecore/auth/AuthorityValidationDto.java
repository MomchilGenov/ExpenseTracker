package com.momchilgenov.springboot.servicecore.auth;

import java.util.List;

/**
 *
 * @param username - the username to be checked for existence in the db and if the @param roles match it
 * @param roles - the claimed roles of the specified user by username
 */
public record AuthorityValidationDto(String username, List<String> roles) {
}
