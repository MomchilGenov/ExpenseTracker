package com.momchilgenov.dbcore.dao.impl;

import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dao.dto.UserAuthenticationStatus;
import com.momchilgenov.dbcore.dao.dto.UserCredentialsStatus;
import com.momchilgenov.dbcore.entity.Role;
import com.momchilgenov.dbcore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    public User update(User user) {
        return this.entityManager.merge(user);
    }

    @Transactional
    @Override
    public User findById(Long id) {
        return this.entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        TypedQuery<User> query = this.entityManager.createQuery("FROM User", User.class)
        return query.getResultList();
    }

    @Transactional
    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = this.entityManager.
                createQuery("FROM User u WHERE u.username=:username", User.class);
        query.setParameter("username", username);
        List<User> singleUser = query.getResultList();
        if (singleUser == null || singleUser.isEmpty()) {
            return null;
        }
        User foundUser = singleUser.get(0);
        User result = new User();
        result.setUsername(foundUser.getUsername());
        result.setRoles(foundUser.getRoles());
        result.setEnabled(foundUser.isEnabled());
        return result;
    }

    @Transactional
    @Override
    public User authenticateUser(User user) {
        TypedQuery<User> query = this.entityManager.
                createQuery("FROM User u WHERE u.username=:username", User.class);
        query.setParameter("username", user.getUsername());
        List<User> singleUser = query.getResultList();
        if (singleUser == null || singleUser.isEmpty()) {
            return null;
        }
        User foundUser = singleUser.get(0);
        //return null if no such user is found,passwords do not match or is banned
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword()) || !foundUser.isEnabled()) {
            return null;
        }
        User result = new User();
        result.setEnabled(true);
        result.setRoles(foundUser.getRoles());
        result.setUsername(foundUser.getUsername());
        //possibly redundant and security issue
        result.setId(foundUser.getId());
        return result;
    }

    @Transactional
    @Override
    public UserAuthenticationStatus validateSubjectExistsAndRolesMatch(String username, List<String> roles) {
        TypedQuery<User> query = this.entityManager.
                createQuery("FROM User u WHERE u.username=:username", User.class);
        query.setParameter("username", username);
        List<User> singleUser = query.getResultList();
        if (singleUser == null || singleUser.isEmpty()) {
            return null;
        }
        User foundUser = singleUser.get(0);
        //return null if no such user is found,passwords do not match or is banned
        if (foundUser == null || !foundUser.isEnabled()) {
            return new UserAuthenticationStatus(UserCredentialsStatus.NOT_FOUND, null, null);
        }
        UserAuthenticationStatus result =
                new UserAuthenticationStatus(UserCredentialsStatus.EXISTS,
                        null, UserCredentialsStatus.ROLES_MISMATCH);
        Set<String> textRoles = foundUser.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        if (textRoles.equals(new HashSet<>(roles))) {
            result =
                    new UserAuthenticationStatus(UserCredentialsStatus.EXISTS,
                            null, UserCredentialsStatus.ROLES_MATCH);
        }

        return result;
    }
}
