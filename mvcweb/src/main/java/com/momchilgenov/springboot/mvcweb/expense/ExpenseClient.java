package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.client.EntityClient;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseClient implements EntityClient<Expense> {
    @Override
    public List<Expense> findAll(String username) {
        return null;
    }

    @Override
    public void create(EntityWithUserDTO<Expense> entityDto) {

    }

    @Override
    public Expense getById(String username, Long id) {
        return null;
    }

    @Override
    public void update(EntityWithUserDTO<Expense> entityDto) {

    }

    @Override
    public void delete(String username, Long id) {

    }
}
