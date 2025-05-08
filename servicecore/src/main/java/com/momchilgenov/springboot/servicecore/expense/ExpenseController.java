package com.momchilgenov.springboot.servicecore.expense;

import com.momchilgenov.springboot.servicecore.dto.EntityWithUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/getById")
    public ExpenseDto getById(@RequestBody EntityWithUserDTO<ExpenseDto> entityDto) {
        return expenseService.getById(entityDto.getUsername(), entityDto.getId());
    }

    @GetMapping("/findAll")
    public List<ExpenseDto> findAll(@RequestBody String username) {
        return expenseService.findAll(username);
    }

    @PostMapping("/create")
    public void create(@RequestBody EntityWithUserDTO<ExpenseDto> entityDto) {
        expenseService.create(entityDto);
    }


    @PutMapping("/update")
    public void update(@RequestBody EntityWithUserDTO<ExpenseDto> entityDto) {
        expenseService.update(entityDto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody EntityWithUserDTO<ExpenseDto> entityDto) {
        expenseService.delete(entityDto.getUsername(), entityDto.getId());
    }

}
