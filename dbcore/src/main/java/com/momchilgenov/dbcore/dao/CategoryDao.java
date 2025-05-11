package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryDao {
    Category findById(Long id);

    Category findById(Long categoryId, Long userId);

    List<Category> findAll();

    List<Category> findAllByUserId(Long userId);

    void save(Category category);

    void saveForUser(Category category, Long userId);

    Category update(Category category);

    Category updateForUser(Category category, Long userId);

    void delete(Long categoryId);

    List<Category> findByCategoryName(String categoryName, Long userId);
}
