package com.momchilgenov.springboot.mvcweb.reports;

import com.momchilgenov.springboot.mvcweb.expense.Expense;
import com.momchilgenov.springboot.mvcweb.expense.ExpenseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseReportService {

    private final ExpenseClient expenseClient;

    @Autowired
    public ExpenseReportService(ExpenseClient expenseClient) {
        this.expenseClient = expenseClient;
    }

    private List<Expense> getFilteredExpenses(LocalDate startDate, LocalDate endDate, String username) {
        List<Expense> allExpenses = expenseClient.findAll(username);
        return allExpenses.stream()
                .filter(expense ->
                        !expense.getDate().isBefore(startDate) &&
                                !expense.getDate().isAfter(endDate)) //include start and end dates
                .toList();
    }

    public Map<String, Double> getGroupedExpenses(LocalDate startDate, LocalDate endDate,
                                                  String groupBy, String username) {
        List<Expense> filteredExpenses = getFilteredExpenses(startDate, endDate, username);
        Map<String, Double> groupedExpenses = null;
        switch (groupBy) {
            case "category":
                groupedExpenses = filteredExpenses.stream()
                        .collect(Collectors.groupingBy(
                                expense -> expense.getCategory().getName(),
                                Collectors.summingDouble(Expense::getAmount)
                        ));
                break;
            case "day":
                //day-month-year
                groupedExpenses = filteredExpenses.stream()
                        .sorted(Comparator.comparing(Expense::getDate))
                        .collect(Collectors.groupingBy(
                                expense -> expense.getDate().toString(),
                                LinkedHashMap::new, // preserve sorting order in result
                                Collectors.summingDouble(Expense::getAmount)
                        ));

                break;
            case "month":
                //month-year
                groupedExpenses = filteredExpenses.stream()
                        .sorted(Comparator.comparing(Expense::getDate))
                        .collect(Collectors.groupingBy(
                                expense -> expense.getDate().getMonth().toString() + " "
                                        + String.valueOf(expense.getDate().getYear()),
                                LinkedHashMap::new, // preserve sorting order in result
                                Collectors.summingDouble(Expense::getAmount)
                        ));

                break;
            case "year":
                //year
                groupedExpenses = filteredExpenses.stream()
                        .sorted(Comparator.comparing(Expense::getDate))

                        .collect(Collectors.groupingBy(
                                expense -> String.valueOf(expense.getDate().getYear()),
                                LinkedHashMap::new, // preserve sorting order in result
                                Collectors.summingDouble(Expense::getAmount)
                        ));

                break;
        }

        return groupedExpenses;
    }

}
