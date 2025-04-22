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

    public UserDto findUserByUsername(String username) {
        User foundUser = this.userDao.findUserByUsername(username);
        if (foundUser == null) {
            return null;
        }
        return new UserDto(foundUser);
    }

    public UserDto authenticateUser(UserDto user) {
        User receivedUser = new User();
        receivedUser.setUsername(user.getUsername());
        receivedUser.setPassword(user.getPassword());
        User foundUser = this.userDao.authenticateUser(receivedUser);
        if (foundUser == null) {
            return null;
        }

        return new UserDto(foundUser);
    }
}
