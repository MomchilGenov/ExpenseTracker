package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Repository
public class AuthRepository {
    private final String URL_OF_FIND_USER_BY_USERNAME;
    private final String URL_OF_AUTHENTICATE_USER;

    public AuthRepository(@Value("${URL_OF_FIND_USER_BY_USERNAME}") String URL_OF_FIND_USER_BY_USERNAME,
                          @Value("${URL_OF_AUTHENTICATE_USER}") String URL_OF_AUTHENTICATE_USER) {
        this.URL_OF_FIND_USER_BY_USERNAME = URL_OF_FIND_USER_BY_USERNAME;
        this.URL_OF_AUTHENTICATE_USER = URL_OF_AUTHENTICATE_USER;
    }

    //todo rename auth repo to AuthenticationDatabaseApiClient or smth like that
    public User authenticateUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(URL_OF_AUTHENTICATE_USER, user, User.class);
    }

    //todo - implement real impl, rename class to a service/client
    public JwtAccessTokenStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles) {
        return new JwtAccessTokenStatus(null, null, false, JwtClaimValidationStatus.EXISTS, true);
    }

    public User findUserByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(URL_OF_FIND_USER_BY_USERNAME, username, User.class);
    }

    public void registerUser(User user) {

    }

}
