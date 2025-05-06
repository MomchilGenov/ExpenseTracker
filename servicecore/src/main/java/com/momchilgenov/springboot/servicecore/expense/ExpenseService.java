package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
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


    public List<ExpenseDto> findAll(String username) {
        return this.expenseClient.findAll(username);
    }


    public void create(EntityWithUserDTO<ExpenseDto> entityDto) {
        this.expenseClient.create(entityDto);
    }


    public ExpenseDto getById(String username, Long id) {
        return this.expenseClient.getById(username, id);
    }

    public void update(EntityWithUserDTO<ExpenseDto> entityDto) {
        this.expenseClient.update(entityDto);
    }

    public void delete(String username, Long id) {
        this.expenseClient.delete(username, id);
    }

}
