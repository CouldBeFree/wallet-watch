package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.service.OperationExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OperationController {

    private final OperationExpenseService operationExpenseService;

    public OperationController(OperationExpenseService operationExpenseService) {
        this.operationExpenseService = operationExpenseService;
    }

    @PostMapping("/operation/expense")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OperationExpense> createOperationExpense(@RequestBody OperationExpenseRequestDto dto) {
        return new ResponseEntity<>(operationExpenseService.createOperationExpense(dto), HttpStatus.CREATED);
    }
}
