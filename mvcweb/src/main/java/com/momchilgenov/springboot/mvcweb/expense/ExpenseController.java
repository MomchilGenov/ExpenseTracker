package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.category.Category;
import com.momchilgenov.springboot.mvcweb.category.CategoryService;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    @Autowired
    public ExpenseController(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String showExpenseDashboard(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Expense> expenseList = expenseService.findAll(username);
        model.addAttribute("expenses", expenseList);
        return "expenses/list";
    }

    @PostMapping("")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntityWithUserDTO<Expense> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setEntity(expense);
        expenseService.create(entityDto);
        return "redirect:/api/v1/expenses";
    }

    @GetMapping("/{id}")
    public String loadEditPageForExpense(@PathVariable long id, Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Category> categories = categoryService.findAll(username);
        model.addAttribute("categories", categories);
        Expense expense = expenseService.getById(username, id);
        model.addAttribute("expense", expense);
        return "expenses/edit";
    }

    @PutMapping("")
    public String editExpenseById(@ModelAttribute("expense") Expense expense) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EntityWithUserDTO<Expense> entityDto = new EntityWithUserDTO<>();
        entityDto.setUsername(username);
        entityDto.setId(expense.getId());
        entityDto.setEntity(expense);
        expenseService.update(entityDto);
        return "redirect:/api/v1/expenses";
    }

    @DeleteMapping("/{id}")
    public String deleteExpenseById(@PathVariable long id) {
        System.out.println("Expense to be deleted, with id = " + id);
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        expenseService.delete(username, id);
        return "redirect:/api/v1/expenses";
    }

    @GetMapping("/create")
    public String showExpenseForm(Model model) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Category> categories = categoryService.findAll(username);
        model.addAttribute("categories", categories);
        model.addAttribute("expense", new Expense(null, 0, null, new Category(null)));
        return "expenses/create";
    }

}
