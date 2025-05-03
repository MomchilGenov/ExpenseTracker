package com.momchilgenov.dbcore.dto;

public class EntityWithUserDto<T> {
    private String username;
    private T entity;

    private Long id;

    public EntityWithUserDto() {

    }

    public EntityWithUserDto(String username, T entity, Long id) {
        this.username = username;
        this.entity = entity;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EntityWithUserDTO{" +
                "username='" + username + '\'' +
                ", entity=" + entity +
                ", id=" + id +
                '}';
    }
}