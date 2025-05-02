package com.momchilgenov.springboot.servicecore.category;

import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/findAll")
    public List<CategoryDto> findAll(@RequestBody String username) {
        return categoryService.findAll(username);
    }

    @PostMapping("/create")
    public void create(@RequestBody EntityWithUserDTO<CategoryDto> entityDto) {
        categoryService.create(entityDto);
    }


    @PostMapping("/getById")
    public CategoryDto getById(@RequestBody EntityWithUserDTO<CategoryDto> entityDto) {
        return categoryService.getById(entityDto.getUsername(), entityDto.getId());
    }

    @PostMapping("/update")
    public void update(@RequestBody EntityWithUserDTO<CategoryDto> entityDto) {
        categoryService.update(entityDto);
    }


    }
