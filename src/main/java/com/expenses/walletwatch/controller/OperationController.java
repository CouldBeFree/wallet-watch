package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.service.OperationExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OperationController {

    private final OperationExpenseService operationExpenseService;

    public OperationController(OperationExpenseService operationExpenseService) {
        this.operationExpenseService = operationExpenseService;
    }

    @PostMapping("/operation/expense")
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

    @DeleteMapping("/operation/expense")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeOperationExpense(@RequestBody OperationExpenseRequestDto dto) {
        return new ResponseEntity<>(operationExpenseService.removeOperationExpense(dto), HttpStatus.OK);
    }
}
