package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.client.EntityClient;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;

import java.util.List;

public class CategoryClient implements EntityClient<Category> {


    @Override
    public List<Category> findAll(String username) {
        return null;
    }

    @Override
    public void create(EntityWithUserDTO<Category> entityDto) {

    }

    @Override
    public Category getById(String username, Long id) {
        return null;
    }

    @Override
    public void update(EntityWithUserDTO<Category> entityDto) {

    }

    @Override
    public void delete(String username, Long id) {

    }
}
