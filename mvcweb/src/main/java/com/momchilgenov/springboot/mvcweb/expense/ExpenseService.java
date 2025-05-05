package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    public List<Expense> findAll(String username) {
        return null;
    }

    public void create(EntityWithUserDTO<Expense> entityDto) {
    }

    public Expense getById(String username, Long id) {
        return null;
    }

    public void update(EntityWithUserDTO<Expense> entityDto) {

    }

    public void delete(String username, Long id) {

    }


}
