package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.client.EntityClient;
import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseClient implements EntityClient<ExpenseDto> {
    @Override
    public List<ExpenseDto> findAll(String username) {
        return null;
    }

    @Override
    public void create(EntityWithUserDTO<ExpenseDto> entityDto) {

    }

    @Override
    public ExpenseDto getById(String username, Long id) {
        return null;
    }

    @Override
    public void update(EntityWithUserDTO<ExpenseDto> entityDto) {

    }

    @Override
    public void delete(String username, Long id) {

    }
}
