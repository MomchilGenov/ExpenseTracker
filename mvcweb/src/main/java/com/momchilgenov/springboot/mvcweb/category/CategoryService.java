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

    public void update(EntityWithUserDTO<Category> entityDto) {
        this.categoryClient.update(entityDto);
    }

    public void delete(String username, Long id) {
        this.categoryClient.delete(username, id);
    }

    //if an expense has this category as set, teh category cannot be deleted until no expenses have it set
    public boolean isDeletable(Long categoryId) {
        return this.categoryClient.isDeletable(categoryId);
    }

    public boolean isCategoryNameDuplicate(String categoryName,String username) {
        return this.categoryClient.isCategoryNameDuplicate(categoryName,username);
    }

}
