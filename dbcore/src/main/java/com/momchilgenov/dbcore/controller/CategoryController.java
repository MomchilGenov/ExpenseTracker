package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.dto.EntityWithUserDto;
import com.momchilgenov.dbcore.entity.Category;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/getById")
    public CategoryDto getById(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        System.out.println("Received category to findById has id = " + entityId + " sent by = " + username);
        CategoryDto dummyDto = new CategoryDto("This is a dummy response");
        dummyDto.setId(entityId);
        return dummyDto;
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        CategoryDto categoryDto = entityDto.getEntity();
        System.out.println("Received category to create has name = " + categoryDto.getName() + " sent by = " + username);
    }

    @PutMapping("/update")
    public void updateCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        CategoryDto categoryDto = entityDto.getEntity();
        System.out.println("Update category with id = " + entityId + " to new name = " +
                categoryDto.getName() + "for user = " + username);


    }


    @DeleteMapping("/delete")
    public void deleteCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        System.out.println("Delete category with id = " + entityId + "for user = " + username);

    }

}
