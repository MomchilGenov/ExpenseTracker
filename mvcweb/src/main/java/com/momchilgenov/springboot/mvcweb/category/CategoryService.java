package com.momchilgenov.springboot.mvcweb.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryClient categoryClient;

    @Autowired
    public CategoryService(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

}
