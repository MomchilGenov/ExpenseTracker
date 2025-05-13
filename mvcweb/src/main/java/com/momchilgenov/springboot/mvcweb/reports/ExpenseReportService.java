package com.momchilgenov.springboot.mvcweb.reports;

import com.momchilgenov.springboot.mvcweb.expense.Expense;
import com.momchilgenov.springboot.mvcweb.expense.ExpenseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseReportService {

    private final ExpenseClient expenseClient;

    @Autowired
    public ExpenseReportService(ExpenseClient expenseClient) {
        this.expenseClient = expenseClient;
    }

    private List<Expense> getFilteredExpenses(LocalDate startDate, LocalDate endDate, String username) {
        return null;
    }

    public Map<String, Double> getGroupedExpenses(String groupBy, List<Expense> filteredExpenses, String username) {
        return null;
    }

}
