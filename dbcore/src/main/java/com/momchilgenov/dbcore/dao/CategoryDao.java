package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.entity.Category;

import java.util.List;

public interface CategoryDao {
    Category findById(Long id);

    List<Category> findAll();

    void save(Category category);

    Category update(Category category);
}
