package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.dto.EntityWithUserDto;
import com.momchilgenov.dbcore.entity.Category;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @PostMapping("/findAll")
    public List<Category> findAll(@RequestBody String username) {
        System.out.println("Received username in findAll method = " + username);
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setName("Travel");
        Category category2 = new Category();
        category2.setName("Fun");
        Category category3 = new Category();
        category3.setName("Tech");
        Category category4 = new Category();
        category4.setName("Debts");
        Category category5 = new Category();
        category5.setName("Other");

        category1.setId(1L);
        category2.setId(2L);
        category3.setId(3L);
        category4.setId(4L);
        category5.setId(5L);
        categories.add(category1);
        categories.add(category2);
        categories.add(category3);
        categories.add(category4);
        categories.add(category5);


        return categories;
    }

}
