package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.category.Category;
import com.momchilgenov.springboot.mvcweb.category.CategoryService;
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

import java.time.LocalDate;
import java.util.ArrayList;
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

        System.out.println("Received expense for creation:");
        System.out.println("Name = " + expense.getName());
        System.out.println("Amount = " + expense.getAmount());
        System.out.println("Date = " + expense.getDate());
        System.out.println("Category:");
        System.out.println("Id = " + expense.getCategory().getId());
        System.out.println("Name = " + expense.getCategory().getName());

        return "redirect:/api/v1/expenses";
    }

    @GetMapping("/{id}")
    public String loadEditPageForExpense(@PathVariable long id, Model model) {
        System.out.println("Received expense with id = " + id);
        List<Category> dummyCategories = new ArrayList<>();
        Category category1 = new Category("Clothes");
        category1.setId(1L);
        Category category2 = new Category("Travel");
        category2.setId(18L);
        Expense expense = new Expense("Testing edit page", 800836,
                LocalDate.now(), category1);
        expense.setId(123L);

        Category category3 = new Category("Loan");
        Category category4 = new Category("Sports");
        Category category5 = new Category("Tech");
        Category category6 = new Category("Other");
        dummyCategories.add(category1);
        dummyCategories.add(category2);
        dummyCategories.add(category3);
        dummyCategories.add(category4);
        dummyCategories.add(category5);
        dummyCategories.add(category6);
        model.addAttribute("categories", dummyCategories);


        model.addAttribute("expense", expense);
        return "expenses/edit";
    }

    @PutMapping("")
    public String editExpenseById(@ModelAttribute("expense") Expense expense) {
        System.out.println("Expense in put method:");
        System.out.println("Name = " + expense.getName());
        System.out.println("Amount = " + expense.getAmount());
        System.out.println("Date = " + expense.getDate());
        System.out.println("Category id = " + expense.getCategory().getId());
        return "redirect:/api/v1/expenses";
    }

    @DeleteMapping("/{id}")
    public String deleteExpenseById(@PathVariable long id) {
        System.out.println("Expense to be deleted, with id = " + id);
        return "redirect:/api/v1/expenses";
    }

    @GetMapping("/create")
    public String showExpenseForm(Model model) {
        //specific user's categories
        //category should not be null so we can set its id and pass it to service to get it from db
        List<Category> dummyCategories = new ArrayList<>();
        Category category1 = new Category("Clothes");
        Category category2 = new Category("Travel");
        category2.setId(18L);
        Category category3 = new Category("Loan");
        Category category4 = new Category("Sports");
        Category category5 = new Category("Tech");
        Category category6 = new Category("Other");
        dummyCategories.add(category1);
        dummyCategories.add(category2);
        dummyCategories.add(category3);
        dummyCategories.add(category4);
        dummyCategories.add(category5);
        dummyCategories.add(category6);
        model.addAttribute("categories", dummyCategories);
        model.addAttribute("expense", new Expense(null, 0, null, new Category(null)));
        return "expenses/create";
    }

}
