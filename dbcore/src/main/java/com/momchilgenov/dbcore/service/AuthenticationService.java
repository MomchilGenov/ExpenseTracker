package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.RoleDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dao.dto.UserDto;
import com.momchilgenov.dbcore.entity.Role;
import com.momchilgenov.dbcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final String DEFAULT_ROLE = "ROLE_USER";

    @Autowired
    public AuthenticationService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
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

    public boolean register(UserDto user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleDao.findByName(DEFAULT_ROLE);
        if (userRole.isPresent()) {
            Role defaultRole = userRole.get();
            roles.add(defaultRole);
        } else {
            throw new RuntimeException("Default role does not exist in database");
        }
        newUser.setRoles(roles);
        userDao.save(newUser);
        return true;
    }
}
