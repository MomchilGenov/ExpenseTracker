package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dao.dto.UserAuthenticationStatus;
import com.momchilgenov.dbcore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(User user) {
        this.entityManager.persist(user);
    }

    @Transactional
    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = this.entityManager.
                createQuery("FROM User u WHERE u.username=:username", User.class);
        query.setParameter("username", username);
        User foundUser = query.getSingleResult();
        if (foundUser == null) {
            return null;
        }
        User result = new User();
        result.setUsername(foundUser.getUsername());
        result.setRoles(foundUser.getRoles());
        result.setEnabled(foundUser.isEnabled());
        return result;
    }

    @Transactional
    @Override
    public User authenticateUser(User user) {
        return null;
    }

    @Transactional
    @Override
    public UserAuthenticationStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles) {
        return null;
    }
}
