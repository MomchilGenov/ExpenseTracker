package com.momchilgenov.springboot.mvcweb.category;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveCategory(@ModelAttribute Category category, BindingResult result, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String categoryName = category.getName();
        if (categoryService.isCategoryNameDuplicate(categoryName, username)) {
            result
                    .rejectValue("name", "error.categoryNameDuplicate",
                            "A category with this name already exists!");
            return "categories/category_form";
        }

        EntityWithUserDTO<Category> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setEntity(category);
        categoryService.create(entityDto);
        return "redirect:/api/v1/categories";
    }

    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category, BindingResult result) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String categoryName = category.getName();
        if (categoryService.isCategoryNameDuplicate(categoryName, username)) {
            result
                    .rejectValue("name", "error.categoryNameDuplicate",
                            "A category with this name already exists!");
            return "categories/category_form";
        }


        EntityWithUserDTO<Category> entityDto = new EntityWithUserDTO<>();
        entityDto.setId(id);
        entityDto.setUsername(username);
        entityDto.setEntity(category);
        categoryService.update(entityDto);
        return "redirect:/api/v1/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable long id, RedirectAttributes redirectAttributes) {
        System.out.println("Deleting category with id = " + id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (categoryService.isDeletable(id)) {
            categoryService.delete(username, id);
        } else {
            redirectAttributes
                    .addAttribute("errorMessage",
                            "Cannot delete category because you have expenses that use it.");

        }

        return "redirect:/api/v1/categories";
    }

}
