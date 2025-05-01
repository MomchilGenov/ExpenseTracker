package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryClient categoryClient;

    @Autowired
    public CategoryService(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }


    public List<Category> findAll(String username) {
        return this.categoryClient.findAll(username);
    }

    public void create(EntityWithUserDTO<Category> entityDto) {
        this.categoryClient.create(entityDto);
    }

    public Category getById(String username, Long id) {
        return this.categoryClient.getById(username, id);
    }

   

}
