package com.momchilgenov.springboot.mvcweb.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public String showCategoriesDashBoard(Model model) {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category("Travel");
        Category category2 = new Category("Fun");
        Category category3 = new Category("Tech");
        Category category4 = new Category("Debts");
        Category category5 = new Category("Other");
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
        model.addAttribute("categories", categories);
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
        Category fetchedCategory = new Category("I was in the db");
        model.addAttribute("category", fetchedCategory);
        fetchedCategory.setId(42L);
        return "categories/category_form";
    }

    @PostMapping("")
    public String saveCategory(@ModelAttribute Category category) {
        System.out.println("Created category = " + category.getName());
        return "redirect:/api/v1/categories";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        System.out.println("Id of edited category = " + id);
        System.out.println("Category updated name = " + category.getName());
        return "redirect:/api/v1/categories";
    }

}
