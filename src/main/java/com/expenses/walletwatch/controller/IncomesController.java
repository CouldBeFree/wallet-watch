package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.IncomeRequestDto;
import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/incomes/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Income>> addIncome(@RequestBody IncomeRequestDto dto) {
        return new ResponseEntity<>(incomeService.createUserIncome(dto), HttpStatus.CREATED);
    }
}
