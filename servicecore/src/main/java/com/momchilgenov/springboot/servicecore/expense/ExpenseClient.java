package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.category.CategoryDto;
import com.momchilgenov.springboot.servicecore.client.EntityClient;
import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExpenseClient implements EntityClient<ExpenseDto> {

    private final RestTemplate restTemplate;

    @Autowired
    public ExpenseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<ExpenseDto> findAll(String username) {

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
