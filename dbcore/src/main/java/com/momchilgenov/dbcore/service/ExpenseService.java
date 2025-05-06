package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.ExpenseDto;
import com.momchilgenov.dbcore.entity.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;
    private final UserDao userDao;

    @Autowired
    public ExpenseService(ExpenseDao expenseDao, UserDao userDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
    }

    public List<Expense> findAll(String username) {
        return null;
    }

    public ExpenseDto getById(Long expenseId, String username) {
        return null;
    }

    public void save(ExpenseDto expenseDto, String username) {
    }

    public void update(ExpenseDto expenseDto, String username) {
    }

    public void delete(Long expenseId) {
    }


}
