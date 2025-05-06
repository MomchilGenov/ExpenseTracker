package com.momchilgenov.dbcore.service;

import com.momchilgenov.dbcore.dao.ExpenseDao;
import com.momchilgenov.dbcore.dao.UserDao;
import com.momchilgenov.dbcore.dto.ExpenseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;
    private final UserDao userDao;

    @Autowired
    public ExpenseService(ExpenseDao expenseDao, UserDao userDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
    }
}
