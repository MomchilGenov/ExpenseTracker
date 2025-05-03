package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showCategoriesDashBoard(Model model) {
        //takes the username from the authentication token
        String currentAuthenticatedUser = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Category> receivedCategories = categoryService.findAll(currentAuthenticatedUser);
        model.addAttribute("categories", receivedCategories);
        return "categories/list";
    }

    @GetMapping("/create")
    public String createCategory(Model model) {
        Category newCategory = new Category("");
        newCategory.setName(null);
        model.addAttribute("category", newCategory);
        return "categories/category_form";
    }

    @GetMapping("/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = categoryService.getById(username, id);
        model.addAttribute("category", category);
        return "categories/category_form";
    }

    @PostMapping("")
    public String saveCategory(@ModelAttribute Category category) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntityWithUserDTO<Category> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setEntity(category);
        categoryService.create(entityDto);
        return "redirect:/api/v1/categories";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        System.out.println("Id of edited category = " + id);
        System.out.println("Category updated name = " + category.getName());
        return "redirect:/api/v1/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable long id) {
        System.out.println("Deleting category with id = " + id);
        return "redirect:/api/v1/categories";
    }

}
