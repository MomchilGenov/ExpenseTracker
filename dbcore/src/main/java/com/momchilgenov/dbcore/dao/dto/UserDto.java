package com.momchilgenov.dbcore.dao.dto;

import com.momchilgenov.dbcore.entity.Role;
import com.momchilgenov.dbcore.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private String username;
    private String password;

    private List<String> roles;

    public UserDto() {
        this.roles = new ArrayList<>();
    }

    public UserDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        if (user.getRoles() == null) {
            this.roles = new ArrayList<>();
        } else {
            this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
