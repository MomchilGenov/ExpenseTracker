package com.momchilgenov.springboot.mvcweb.client;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;

import java.util.List;

public interface EntityClient<T> {
    List<T> findAll(String username);

    void create(EntityWithUserDTO<T> entityDto);

    T getById(String username, Long id);

    void update(EntityWithUserDTO<T> entityDto);

    void delete(String username, Long id);
}
