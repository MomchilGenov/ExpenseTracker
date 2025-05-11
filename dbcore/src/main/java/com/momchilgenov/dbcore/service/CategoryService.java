package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.CategoryDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.entity.Category;
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
        Long userId = userDao.findUserIdByUsername(username);
        return categoryDao.findAllByUserId(userId);
    }

    public CategoryDto getById(Long categoryId, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Category foundCategory = categoryDao.findById(categoryId, userId);
        CategoryDto categoryDto = new CategoryDto(foundCategory.getName());
        categoryDto.setId(foundCategory.getId());
        return categoryDto;
    }

    public void save(CategoryDto categoryDto, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setUserId(userId);
        categoryDao.saveForUser(category, userId);
    }

    public void update(CategoryDto categoryDto, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setUserId(userId);
        categoryDao.updateForUser(category, userId);
    }

    public void delete(Long categoryId) {
        this.categoryDao.delete(categoryId);
    }

    public boolean isCategoryNameDuplicate(String categoryName, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        List<Category> categories = categoryDao.findByCategoryName(categoryName, userId);
        return categories != null && !categories.isEmpty();
    }
}
