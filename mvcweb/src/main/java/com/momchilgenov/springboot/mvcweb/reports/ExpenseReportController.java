package com.momchilgenov.springboot.mvcweb.reports;

import jakarta.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class ExpenseReportController {

    private final ExpenseReportService expenseReportService;

    @Autowired
    public ExpenseReportController(ExpenseReportService expenseReportService) {
        this.expenseReportService = expenseReportService;
    }


    @GetMapping("/expense-report-filtered")
    public void generateExpenseReportByFilter(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,
            @RequestParam(defaultValue = "category")
            String groupBy,
            HttpServletResponse response) throws IOException {

        // Fallback defaults if null
        if (startDate == null) {
            startDate = LocalDate.of(2000, 1, 1);
        }
        if (endDate == null) {
            endDate = LocalDate.now();
        }

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Grouping
        Map<String, Double> groupedData = expenseReportService.getGroupedExpenses(startDate, endDate, groupBy, username);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : groupedData.entrySet()) {
            dataset.setValue(entry.getValue(), "Expenses", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Expense Report", groupBy.toUpperCase(), "Amount", dataset,
                PlotOrientation.VERTICAL, false, true, false
        );

        response.setContentType("image/png");
        ChartUtils.writeChartAsPNG(response.getOutputStream(), chart, 800, 400);

    }

    @GetMapping("/report_by")
    public String getReportByGroupPage(@RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate startDate,
                                       @RequestParam(required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate endDate,
                                       @RequestParam(defaultValue = "category")
                                       String groupBy,
                                       Model model) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("groupBy", groupBy);

        return "expenses/report_by_group";
    }
}
