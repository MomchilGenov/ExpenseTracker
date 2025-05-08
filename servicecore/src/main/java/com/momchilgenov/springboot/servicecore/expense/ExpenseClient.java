package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.category.CategoryDto;
import com.momchilgenov.springboot.servicecore.client.EntityClient;
import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExpenseClient implements EntityClient<ExpenseDto> {

    private final String URL_OF_FIND_ALL_EXPENSES;
    private final String URL_OF_CREATE_EXPENSE;
    private final String URL_OF_GET_EXPENSE_BY_ID;
    private final String URL_OF_UPDATE_EXPENSE;
    private final String URL_OF_DELETE_EXPENSE;

    private final RestTemplate restTemplate;

    @Autowired
    public ExpenseClient(RestTemplate restTemplate,
                         @Value("${URL_OF_FIND_ALL_EXPENSES}") String URL_OF_FIND_ALL_EXPENSES,
                         @Value("${URL_OF_CREATE_EXPENSE}") String URL_OF_CREATE_EXPENSE,
                         @Value("${URL_OF_GET_EXPENSE_BY_ID}") String URL_OF_GET_EXPENSE_BY_ID,
                         @Value("${URL_OF_UPDATE_EXPENSE}") String URL_OF_UPDATE_EXPENSE,
                         @Value("${URL_OF_DELETE_EXPENSE}") String URL_OF_DELETE_EXPENSE) {
        this.restTemplate = restTemplate;
        this.URL_OF_FIND_ALL_EXPENSES = URL_OF_FIND_ALL_EXPENSES;
        this.URL_OF_CREATE_EXPENSE = URL_OF_CREATE_EXPENSE;
        this.URL_OF_GET_EXPENSE_BY_ID = URL_OF_GET_EXPENSE_BY_ID;
        this.URL_OF_UPDATE_EXPENSE = URL_OF_UPDATE_EXPENSE;
        this.URL_OF_DELETE_EXPENSE = URL_OF_DELETE_EXPENSE;
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
