package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.category.Category;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class Expense {
    //name amount date category
    private long id;
    private String name;
    private double amount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // parses in yyyy-MM-dd format
    private LocalDate date;
    private Category category;

    public Expense(String name, double amount, LocalDate date, Category category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
