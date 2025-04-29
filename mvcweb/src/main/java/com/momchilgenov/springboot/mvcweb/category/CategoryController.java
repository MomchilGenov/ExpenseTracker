package com.momchilgenov.springboot.mvcweb.category;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "categories/list";
    }

    

}
