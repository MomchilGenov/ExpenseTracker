package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Repository
public class AuthRepository {
    private final String URL_OF_FIND_USER_BY_USERNAME;
    private final String URL_OF_AUTHENTICATE_USER;
    private final String URL_OF_REGISTER_USER;
    private final String URL_OF_VALIDATE_AUTHORITY;

    public AuthRepository(@Value("${URL_OF_FIND_USER_BY_USERNAME}") String URL_OF_FIND_USER_BY_USERNAME,
                          @Value("${URL_OF_AUTHENTICATE_USER}") String URL_OF_AUTHENTICATE_USER,
                          @Value("${URL_OF_REGISTER_USER}") String URL_OF_REGISTER_USER,
                          @Value("${URL_OF_VALIDATE_AUTHORITY}") String URL_OF_VALIDATE_AUTHORITY) {
        this.URL_OF_FIND_USER_BY_USERNAME = URL_OF_FIND_USER_BY_USERNAME;
        this.URL_OF_AUTHENTICATE_USER = URL_OF_AUTHENTICATE_USER;
        this.URL_OF_REGISTER_USER = URL_OF_REGISTER_USER;
        this.URL_OF_VALIDATE_AUTHORITY = URL_OF_VALIDATE_AUTHORITY;
    }

    //todo rename auth repo to AuthenticationDatabaseApiClient or smth like that
    public User authenticateUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(URL_OF_AUTHENTICATE_USER, user, User.class);
    }
    
    public JwtAccessTokenStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles) {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setUsername(username);
        user.setRoles(roles);
        AuthorityValidationDto result = restTemplate.postForObject(URL_OF_VALIDATE_AUTHORITY, user,
                AuthorityValidationDto.class);
        if (result.username() == null) {
            return new JwtAccessTokenStatus(null, null, false,
                    JwtClaimValidationStatus.NOT_FOUND, false);
        }
        if (result.roles() == null) {
            return new JwtAccessTokenStatus(null, null, false,
                    JwtClaimValidationStatus.EXISTS, false);

        }
        return new JwtAccessTokenStatus(null, null, false, JwtClaimValidationStatus.EXISTS, true);
    }

    public User findUserByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(URL_OF_FIND_USER_BY_USERNAME, username, User.class);
    }

    public void registerUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        //redundant return class type, future impls will aim to remove synchronous communication all together
        restTemplate.postForObject(URL_OF_REGISTER_USER, user, Boolean.class);
    }

}
