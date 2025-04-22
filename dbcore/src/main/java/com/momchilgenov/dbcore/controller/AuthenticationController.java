package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dao.dto.UserDto;
import com.momchilgenov.dbcore.entity.Role;
import com.momchilgenov.dbcore.entity.User;
import com.momchilgenov.dbcore.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
