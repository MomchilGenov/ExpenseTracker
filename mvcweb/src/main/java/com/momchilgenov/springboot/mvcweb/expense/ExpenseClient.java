package com.momchilgenov.springboot.mvcweb.expense;

import com.momchilgenov.springboot.mvcweb.client.EntityClient;
import com.momchilgenov.springboot.mvcweb.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExpenseClient implements EntityClient<Expense> {

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
        this.URL_OF_FIND_ALL_EXPENSES = URL_OF_FIND_ALL_EXPENSES;
        this.URL_OF_CREATE_EXPENSE = URL_OF_CREATE_EXPENSE;
        this.URL_OF_GET_EXPENSE_BY_ID = URL_OF_GET_EXPENSE_BY_ID;
        this.URL_OF_UPDATE_EXPENSE = URL_OF_UPDATE_EXPENSE;
        this.URL_OF_DELETE_EXPENSE = URL_OF_DELETE_EXPENSE;
        this.restTemplate = restTemplate;


    }


    @Override
    public List<Expense> findAll(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(username, headers);
        ResponseEntity<List<Expense>> response = restTemplate.exchange(
                URL_OF_FIND_ALL_EXPENSES,
                HttpMethod.POST, //either this or we need to send the username as a url query param which for sec reason is bad
                //spring by default removes/doesn't  look at the body of a get request
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @Override
    public void create(EntityWithUserDTO<Expense> entityDto) {

    }

    @Override
    public Expense getById(String username, Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        EntityWithUserDTO<Expense> expenseDto = new EntityWithUserDTO<>();
        expenseDto.setId(id);
        expenseDto.setUsername(username);
        HttpEntity<EntityWithUserDTO<Expense>> requestEntity = new HttpEntity<>(expenseDto, headers);
        ResponseEntity<Expense> response = restTemplate.exchange(
                URL_OF_GET_EXPENSE_BY_ID,
                HttpMethod.POST, //either this or we need to send the username as a url query param which for sec reason is bad
                //spring by default removes/doesn't  look at the body of a get request
                requestEntity,
                Expense.class
        );
        return response.getBody();
    }

    @Override
    public void update(EntityWithUserDTO<Expense> entityDto) {

    }

    @Override
    public void delete(String username, Long id) {

    }
}
