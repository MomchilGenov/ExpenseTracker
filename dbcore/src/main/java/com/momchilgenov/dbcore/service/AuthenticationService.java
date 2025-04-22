package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dao.dto.UserDto;
import com.momchilgenov.dbcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }


}
