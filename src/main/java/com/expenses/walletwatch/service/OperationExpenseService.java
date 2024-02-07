package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationExpenseDao;
import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationExpenseService {
    private final OperationExpenseDao expenseDao;

    public OperationExpenseService(OperationExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public OperationExpenseResponseDto createOperationExpense(OperationExpenseRequestDto dto) {
        try {
            OperationExpense operationExpense = expenseDao.createOperationExpense(dto, 6);
            return new OperationExpenseResponseDto(
                    operationExpense.getId(),
                    operationExpense.getDate(),
                    operationExpense.getExpenses_category_name(),
                    operationExpense.getAmount()
            );
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }

    public String removeOperationExpense(int expenseId) {
        try {
            expenseDao.removeOperationExpense(6, expenseId);
            return "Removed";
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest(e.getCause());
        }
    }
    
    public OperationExpenseResponseDto getOperationExpenseById(int expenseId) {
        OperationExpense expense = expenseDao.getOperationExpenseById(6, expenseId);
        return new OperationExpenseResponseDto(
                expense.getId(),
                expense.getDate(),
                expense.getExpenses_category_name(),
                expense.getAmount()
        );
    }

    public OperationExpenseResponseDto updateOperationExpense(OperationExpenseRequestDto dto, int expenseId) {
        OperationExpense expense = expenseDao.updateOperationExpense(dto, 6, expenseId);
        return new OperationExpenseResponseDto(
                expense.getId(),
                expense.getDate(),
                expense.getExpenses_category_name(),
                expense.getAmount()
        );
    }

    public List<OperationExpenseResponseDto> getAllOperationExpenses() {
        List<OperationExpense> value = expenseDao.getAllOperationExpenses(6);
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
        return operationExpenseResponseDtos;
    }
}
