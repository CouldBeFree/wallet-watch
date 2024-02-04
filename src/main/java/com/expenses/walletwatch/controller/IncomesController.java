package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.service.ExpenseService;
import com.expenses.walletwatch.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomesController {
    private final IncomeService incomeService;

    public IncomesController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/incomes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Income>> getExpenses() {
        return new ResponseEntity<>(incomeService.getAllIncomes(), HttpStatus.OK);
    }
}
