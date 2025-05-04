package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
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
