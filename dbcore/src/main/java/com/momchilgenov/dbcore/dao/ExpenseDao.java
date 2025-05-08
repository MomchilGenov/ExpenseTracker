package com.momchilgenov.dbcore.dao;

import com.momchilgenov.dbcore.dto.ExpenseDto;
import com.momchilgenov.dbcore.entity.Category;
import com.momchilgenov.dbcore.entity.Expense;

import java.util.List;

public interface ExpenseDao {

    Expense findById(Long id);

    Expense findById(Long expenseId, Long userId);

    List<Expense> findAll();

    List<ExpenseDto> findAllByUserId(Long userId);

    void save(Expense expense);

    void saveForUser(Expense expense, Long userId);

    Expense update(Expense expense);

    Expense updateForUser(Expense expense, Long userId);

    void delete(Long expenseId);

}
