package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dao.dto.UserDto;
import com.momchilgenov.dbcore.entity.Role;
import com.momchilgenov.dbcore.entity.User;
import com.momchilgenov.dbcore.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //post mapping, because otherwise username is exposed; security reasons
    @PostMapping("/user")
    public UserDto getUserByUsername(@RequestBody String username) {
        return authenticationService.findUserByUsername(username);
    }

    @PostMapping("/authenticateUser")
    public UserDto authenticateUser(@RequestBody UserDto user) {
        return authenticationService.authenticateUser(user);
    }

    @PostMapping("register")
    public boolean registerUser(@RequestBody UserDto user){
        return authenticationService.register(user);
    }

}
