package com.momchilgenov.springboot.servicecore.category;

import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
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

    public List<CategoryDto> findAll(String username) {
        return this.categoryClient.findAll(username);
    }


    public void create(EntityWithUserDTO<CategoryDto> entityDto) {
        this.categoryClient.create(entityDto);
    }


    public CategoryDto getById(String username, Long id) {
        return this.categoryClient.getById(username, id);
    }

    public void update(EntityWithUserDTO<CategoryDto> entityDto) {
        this.categoryClient.update(entityDto);
    }
    

}
