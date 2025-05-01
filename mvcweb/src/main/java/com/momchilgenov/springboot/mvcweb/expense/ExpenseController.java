package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.category.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    @GetMapping("")
    public String showExpenseDashboard(Model model) {
        var today = LocalDate.now();
        var category = new Category("Clothes");
        List<Expense> expenses = new ArrayList<>();
        expenses.add(new Expense("Shoes", 55, today, category));
        expenses.add(new Expense("Shirt", 25, today, category));
        expenses.add(new Expense("Shirt", 35, today, category));
        expenses.add(new Expense("Blue shirt", 25, today, category));
        expenses.add(new Expense("Trousers", 40, today, category));
        expenses.add(new Expense("Glasses", 80, today, category));
        expenses.add(new Expense("T-shirt", 20, today, category));
        expenses.add(new Expense("Belt", 15, today, category));
        expenses.add(new Expense("Green jacket", 90, today, category));
        expenses.add(new Expense("Yellow hat", 5, today, category));
        expenses.add(new Expense("Red-dotted socks", 10, today, category));
        expenses.add(new Expense("Blue tie", 12, today, category));

        expenses.add(new Expense("Green jacket", 90, today, category));
        expenses.add(new Expense("Yellow hat", 5, today, category));
        expenses.add(new Expense("Red-dotted socks", 10, today, category));
        expenses.add(new Expense("Blue tie", 12, today, category));

        expenses.add(new Expense("Green jacket", 90, today, category));
        expenses.add(new Expense("Yellow hat", 5, today, category));
        expenses.add(new Expense("Red-dotted socks", 10, today, category));
        expenses.add(new Expense("Blue tie", 12, today, category));

        expenses.add(new Expense("Green jacket", 90, today, category));
        expenses.add(new Expense("Yellow hat", 5, today, category));
        expenses.add(new Expense("Red-dotted socks", 10, today, category));
        expenses.add(new Expense("Blue tie", 12, today, category));
        model.addAttribute("expenses", expenses);
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
        expense.setId(123);

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
