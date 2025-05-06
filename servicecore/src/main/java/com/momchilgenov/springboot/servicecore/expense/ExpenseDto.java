package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.category.CategoryDto;

import java.time.LocalDate;

public class ExpenseDto {
    private long id;
    private String name;
    private double amount;
    private LocalDate date;
    private CategoryDto category;

    public ExpenseDto() {

    }

    public ExpenseDto(String name, double amount, LocalDate date, CategoryDto category) {
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

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

}
