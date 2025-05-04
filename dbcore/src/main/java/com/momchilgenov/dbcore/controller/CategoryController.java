package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.dto.EntityWithUserDto;
import com.momchilgenov.dbcore.entity.Category;
import com.momchilgenov.dbcore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/findAll")
    public List<Category> findAll(@RequestBody String username) {
        return categoryService.findAll(username);
    }

    @PostMapping("/getById")
    public CategoryDto getById(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long categoryId = entityDto.getId();
        return categoryService.getById(categoryId, username);
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        CategoryDto categoryDto = entityDto.getEntity();
        categoryService.save(categoryDto, username);
    }

    @PutMapping("/update")
    public void updateCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        CategoryDto categoryDto = entityDto.getEntity();
        categoryService.update(categoryDto, username);
    }


    @DeleteMapping("/delete")
    public void deleteCategory(@RequestBody EntityWithUserDto<CategoryDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        System.out.println("Delete category with id = " + entityId + "for user = " + username);

    }

}
