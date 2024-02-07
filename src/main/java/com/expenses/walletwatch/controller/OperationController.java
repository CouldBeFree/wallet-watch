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
@RequestMapping("/api/operation/expense")
public class OperationController {

    private final OperationExpenseService operationExpenseService;

    public OperationController(OperationExpenseService operationExpenseService) {
        this.operationExpenseService = operationExpenseService;
    }

    @PostMapping("")
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

    @GetMapping("")
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

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeOperationExpense(@RequestBody OperationExpenseRequestDto dto) {
        return new ResponseEntity<>(operationExpenseService.removeOperationExpense(dto), HttpStatus.OK);
    }
}
