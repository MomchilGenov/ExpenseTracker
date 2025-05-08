package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.CategoryDao;
import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.CategoryDto;
import com.momchilgenov.dbcore.dto.ExpenseDto;
import com.momchilgenov.dbcore.entity.Category;
import com.momchilgenov.dbcore.entity.Expense;
import com.momchilgenov.dbcore.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Autowired
    public ExpenseService(ExpenseDao expenseDao, UserDao userDao, CategoryDao categoryDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
        this.categoryDao = categoryDao;
    }

    public List<Expense> findAll(String username) {
        Long userId = userDao.findUserIdByUsername(username);
        return expenseDao.findAllByUserId(userId);
    }

    public ExpenseDto getById(Long expenseId, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Expense expense = expenseDao.findById(expenseId, userId);
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setId(expense.getId());
        expenseDto.setAmount(expense.getAmount());
        expenseDto.setName(expense.getName());
        expenseDto.setDate(expense.getDate());
        CategoryDto categoryDto = new CategoryDto(expense.getCategory().getName());
        categoryDto.setId(expense.getCategory().getId());
        expenseDto.setCategory(categoryDto);
        return expenseDto;
    }

    public void save(ExpenseDto expenseDto, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Expense expense = new Expense();
        expense.setDate(expenseDto.getDate());
        expense.setName(expenseDto.getName());
        expense.setAmount(expenseDto.getAmount());
        Category category = categoryDao.findById(expenseDto.getCategory().getId());
        expense.setCategory(category);
        expenseDao.saveForUser(expense, userId);
    }

    public void update(ExpenseDto expenseDto, String username) {
        Long userId = userDao.findUserIdByUsername(username);
        Expense expense = expenseDao.findById(expenseDto.getId(), userId);
        expense.setDate(expenseDto.getDate());
        expense.setName(expenseDto.getName());
        expense.setAmount(expenseDto.getAmount());
        Category category = categoryDao.findById(expenseDto.getCategory().getId());
        expense.setCategory(category);
        expenseDao.updateForUser(expense, userId);
    }

    public void delete(Long expenseId) {
    }


}
