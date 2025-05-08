package com.momchilgenov.dbcore.controller;

import com.momchilgenov.dbcore.dto.EntityWithUserDto;
import com.momchilgenov.dbcore.dto.ExpenseDto;
import com.momchilgenov.dbcore.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //post mapped, because otherwise we need to send the username as a query param, which for security reasons is bad
    @PostMapping("/findAll")
    public List<ExpenseDto> findAll(@RequestBody String username) {
        return expenseService.findAll(username);
    }

    @GetMapping("/getById")
    public ExpenseDto getById(@RequestBody EntityWithUserDto<ExpenseDto> entityDto) {
        String username = entityDto.getUsername();
        Long expenseId = entityDto.getId();
        return expenseService.getById(expenseId, username);
    }

    @PostMapping("/create")
    public void createExpense(@RequestBody EntityWithUserDto<ExpenseDto> entityDto) {
        String username = entityDto.getUsername();
        ExpenseDto expenseDto = entityDto.getEntity();
        expenseService.save(expenseDto, username);
    }

    @PutMapping("/update")
    public void updateExpense(@RequestBody EntityWithUserDto<ExpenseDto> entityDto) {
        String username = entityDto.getUsername();
        Long entityId = entityDto.getId();
        ExpenseDto expenseDto = entityDto.getEntity();
        expenseService.update(expenseDto, username);
    }


    @DeleteMapping("/delete")
    public void deleteExpense(@RequestBody EntityWithUserDto<ExpenseDto> entityDto) {
        String username = entityDto.getUsername();
        Long expenseId = entityDto.getId();
        expenseService.delete(expenseId);
    }


}
