package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.dao.dto.UserAuthenticationStatus;
import com.momchilgenov.dbcore.entity.User;

import java.util.List;

public interface UserDao {

    //password is only received and used for registering users with save(user) or
    //for matching the password with a username

    //for registration
    void save(User user);

    //with roles, without password
    User findUserByUsername(String username);


    /**
     * @param user - a user dto object with a username and password. This method should check
     *             if such a user exists and if yes, does the password match the username
     * @return - a user object populated with the received username and valid roles, without returning the password.
     * If no such user exists or there is a password mismatch - return null.
     */
    User authenticateUser(User user);

    /**
     * @param username - username of the user; to be checked if it exists.
     * @param roles    - check if a user with a username of @param username has these exact roles
     * @return - a UserAuthenticationStatus object with properly set fields denoting the status
     * of the authentication check
     */
    UserAuthenticationStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles);

}
