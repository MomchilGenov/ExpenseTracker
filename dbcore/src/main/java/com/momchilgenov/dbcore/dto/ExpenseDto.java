package com.momchilgenov.dbcore.dto;

import com.momchilgenov.dbcore.entity.Expense;

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
    public ExpenseDto(Expense expense) {
        this.id = expense.getId();
        this.name = expense.getName();
        this.amount = expense.getAmount();
        this.date = expense.getDate();
        this.category = new CategoryDto(expense.getCategory().getName());
        this.category.setId(expense.getId());
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
