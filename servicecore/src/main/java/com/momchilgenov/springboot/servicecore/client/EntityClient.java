package com.momchilgenov.springboot.servicecore.client;

import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;

import java.util.List;

public interface EntityClient<T> {
    List<T> findAll(String username);

    void create(EntityWithUslerDTO<T> entityDto);

    T getById(String username, Long id);

    void update(EntityWithUserDTO<T> entityDto);

    void delete(String username, Long id);
}
