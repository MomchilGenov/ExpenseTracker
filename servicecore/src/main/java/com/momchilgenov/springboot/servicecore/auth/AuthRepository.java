package com.momchilgenov.springboot.servicecore.auth;

import com.momchilgenov.springboot.servicecore.User;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthRepository {

    public User authenticateUser(User user) {
        //can replace with a dummy in memory db for more users and role testing.
        List<String> dummyRoles = new ArrayList<>();
        dummyRoles.add("ROLE_USER");
        dummyRoles.add("ROLE_ADMIN");
        return new User("Ivan Samuilov", null, dummyRoles);
    }

}
