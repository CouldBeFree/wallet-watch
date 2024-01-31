package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getExpenses() {
        return new ResponseEntity<>(expenseService.getExpensesCategories(), HttpStatus.OK);
    }

    @PostMapping("/expenses/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Expense>> addExpense(@RequestBody ExpenseRequestDto dto) {
        return new ResponseEntity<>(expenseService.getUserExpensesCategories(dto), HttpStatus.CREATED);
    }
}
