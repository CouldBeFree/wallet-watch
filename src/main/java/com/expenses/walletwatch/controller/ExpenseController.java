package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expense")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getExpenses() {
        return new ResponseEntity<>(expenseService.getExpensesCategories(), HttpStatus.OK);
    }
}
