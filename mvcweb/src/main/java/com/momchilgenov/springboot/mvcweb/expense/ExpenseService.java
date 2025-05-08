package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseClient expenseClient;

    @Autowired
    public ExpenseService(ExpenseClient expenseClient) {
        this.expenseClient = expenseClient;
    }

    public List<Expense> findAll(String username) {
        return expenseClient.findAll(username);
    }

    public void create(EntityWithUserDTO<Expense> entityDto) {
        expenseClient.create(entityDto);
    }

    public Expense getById(String username, Long id) {
        return expenseClient.getById(username, id);
    }

    public void update(EntityWithUserDTO<Expense> entityDto) {

    }

    public void delete(String username, Long id) {

    }


}
