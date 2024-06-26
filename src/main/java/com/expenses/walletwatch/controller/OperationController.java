package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.dto.OperationIncomeResponseDto;
import com.expenses.walletwatch.service.OperationExpenseService;
import com.expenses.walletwatch.service.OperationIncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/operation")
public class OperationController {

    private final OperationExpenseService operationExpenseService;
    private final OperationIncomeService operationIncomeService;

    public OperationController(OperationExpenseService operationExpenseService, OperationIncomeService operationIncomeService) {
        this.operationExpenseService = operationExpenseService;
        this.operationIncomeService = operationIncomeService;
    }

    @PostMapping("/income")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationIncomeResponseDto> createOperationIncome(@RequestBody OperationIncomeRequestDto dto) {
        return new ResponseEntity<>(operationIncomeService.createOperationIncome(dto), HttpStatus.CREATED);
    }

    @GetMapping("/income")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OperationIncomeResponseDto>> getAllOperationIncomes(@RequestParam Optional<String> startDate, Optional<String> endDate) {
        return new ResponseEntity<>(operationIncomeService.getAllOperationIncomes(startDate, endDate), HttpStatus.OK);
    }

    @PutMapping("/income/{incomeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationIncomeResponseDto> updateOperationIncome(@RequestBody OperationIncomeRequestDto dto, @PathVariable int incomeId) {
        return new ResponseEntity<>(operationIncomeService.updateOperationIncome(dto, incomeId), HttpStatus.OK);
    }

    @DeleteMapping("/income/{incomeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeOperationIncome(@PathVariable int incomeId) {
        return new ResponseEntity<>(operationIncomeService.removeOperationIncome(incomeId), HttpStatus.OK);
    }

    @PostMapping("/expense")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationExpenseResponseDto> createOperationExpense(@RequestBody OperationExpenseRequestDto dto) {
        return new ResponseEntity<>(operationExpenseService.createOperationExpense(dto), HttpStatus.CREATED);
    }

    @GetMapping("/expense")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OperationExpenseResponseDto>> getAllOperationsExpenses(@RequestParam Optional<String> startDate, Optional<String> endDate) {
        return new ResponseEntity<>(operationExpenseService.getAllOperationExpenses(startDate, endDate), HttpStatus.OK);
    }

    @PutMapping("/expense/{expenseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationExpenseResponseDto> updateOperationExpense(@RequestBody OperationExpenseRequestDto dto, @PathVariable int expenseId) {
        return new ResponseEntity<>(operationExpenseService.updateOperationExpense(dto, expenseId), HttpStatus.OK);
    }

    @GetMapping("/expense/{expenseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OperationExpenseResponseDto> getOperationExpenseById(@PathVariable int expenseId) {
        return new ResponseEntity<>(operationExpenseService.getOperationExpenseById(expenseId), HttpStatus.OK);
    }

    @DeleteMapping("/expense/{expenseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeOperationExpense(@PathVariable int expenseId) {
        return new ResponseEntity<>(operationExpenseService.removeOperationExpense(expenseId), HttpStatus.OK);
    }
}
