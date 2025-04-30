package com.momchilgenov.springboot.mvcweb.dto;

public class EntityWithUserDTO<T> {
    private String username;
    private T entity;

    public EntityWithUserDTO() {

    }

    public EntityWithUserDTO(String username, T entity) {
        this.username = username;
        this.entity = entity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "EntityWithUserDTO{" +
                "username='" + username + '\'' +
                ", entity=" + entity +
                '}';
    }
}
