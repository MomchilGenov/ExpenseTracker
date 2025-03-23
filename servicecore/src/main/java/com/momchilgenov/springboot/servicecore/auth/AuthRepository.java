package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Repository
public class AuthRepository {

    //todo rename auth repo to AuthenticationDatabaseApiClient or smth like that
    public User authenticateUser(User user) {
        //can replace with a dummy in memory db for more users and role testing.
        //todo - check for user in a db, if not present, return null, this is directly linked with
        //JwtAuthProvider return statement and placing the same username that was received.

        List<String> dummyRoles = new ArrayList<>();
        dummyRoles.add("ROLE_USER");
        dummyRoles.add("ROLE_ADMIN");
        return new User("Ivan Samuilov", null, dummyRoles);
    }

    //todo - implement real impl, rename class to a service/client
    public JwtAccessTokenStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles) {
        return new JwtAccessTokenStatus(null, null, false, JwtClaimValidationStatus.EXISTS, true);
    }


}
