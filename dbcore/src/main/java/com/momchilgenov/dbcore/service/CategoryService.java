package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.CategoryDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.entity.Category;
import com.momchilgenov.dbcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    @Autowired
    public CategoryService(CategoryDao categoryDao, UserDao userDao) {
        this.categoryDao = categoryDao;
        this.userDao = userDao;
    }

    public List<Category> findAll(String username) {
        return null;
    }

    public CategoryDto getById(Long categoryId, String username) {
        return null;
    }

    public void save(CategoryDto category, String username) {

    }

    public void update(CategoryDto categoryDto, String username) {

    }

    public void delete(Long categoryId) {

    }
}
