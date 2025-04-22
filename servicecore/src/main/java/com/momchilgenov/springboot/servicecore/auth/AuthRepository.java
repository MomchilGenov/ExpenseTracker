package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Repository
public class AuthRepository {
    private final String URL_OF_FIND_USER_BY_USERNAME;

    public AuthRepository(@Value("${URL_OF_FIND_USER_BY_USERNAME}") String URL_OF_FIND_USER_BY_USERNAME) {
        this.URL_OF_FIND_USER_BY_USERNAME = URL_OF_FIND_USER_BY_USERNAME;
    }

    //todo rename auth repo to AuthenticationDatabaseApiClient or smth like that
    public User authenticateUser(User user) {
        //can replace with a dummy in memory db for more users and role testing.
        //todo - check for user in a db, if not present, return null, this is directly linked with
        //JwtAuthProvider return statement and placing the same username that was received.
        //todo - see same problems for findUserByUsername()
        List<String> dummyRoles = new ArrayList<>();
        dummyRoles.add("ROLE_USER");
        dummyRoles.add("ROLE_ADMIN");
        return new User("Ivan Samuilov", null, dummyRoles);
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
