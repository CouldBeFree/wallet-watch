package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.service.OperationExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    private final OperationExpenseService operationExpenseService;

    public OperationController(OperationExpenseService operationExpenseService) {
        this.operationExpenseService = operationExpenseService;
    }

    @PostMapping("/expense")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationExpenseResponseDto> createOperationExpense(@RequestBody OperationExpenseRequestDto dto) {
        OperationExpense operationExpense = operationExpenseService.createOperationExpense(dto);
        OperationExpenseResponseDto operationExpenseRequestDto = new OperationExpenseResponseDto(
                operationExpense.getId(),
                operationExpense.getDate(),
                operationExpense.getExpenses_category_name(),
                operationExpense.getAmount()
        );
        return new ResponseEntity<>(operationExpenseRequestDto, HttpStatus.CREATED);
    }

    @GetMapping("/expense")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OperationExpenseResponseDto>> getAllOperationsExpenses() {
        List<OperationExpense> value = operationExpenseService.getAllOperationExpenses();
        List<OperationExpenseResponseDto> operationExpenseResponseDtos = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            OperationExpense operationExpense = value.get(i);
            OperationExpenseResponseDto operationExpenseRequestDto = new OperationExpenseResponseDto(
                    operationExpense.getId(),
                    operationExpense.getDate(),
                    operationExpense.getExpenses_category_name(),
                    operationExpense.getAmount()
            );
            operationExpenseResponseDtos.add(operationExpenseRequestDto);
        }
        return new ResponseEntity<>(operationExpenseResponseDtos, HttpStatus.OK);
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
