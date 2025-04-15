package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.entity.Expense;

import java.util.List;

public interface ExpenseDao {

    Expense findById(Long id);

    List<Expense> findAll();

    void save(Expense expense);

    Expense update(Expense expense);

}
