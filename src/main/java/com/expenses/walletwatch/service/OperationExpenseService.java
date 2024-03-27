package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationExpenseDao;
import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OperationExpenseService {
    private final OperationExpenseDao expenseDao;
    private final GetUserData getUserData;

    public OperationExpenseService(OperationExpenseDao expenseDao, GetUserData getUserData) {
        this.expenseDao = expenseDao;
        this.getUserData = getUserData;
    }

    public OperationExpenseResponseDto createOperationExpense(OperationExpenseRequestDto dto) {
        Long userId = getUserData.getUserIdFromToken();
        try {
            OperationExpense operationExpense = expenseDao.createOperationExpense(dto, userId);
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
        Long userId = getUserData.getUserIdFromToken();
        try {
            expenseDao.removeOperationExpense(userId, expenseId);
            return "Removed";
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest(e.getCause());
        }
    }
    
    public OperationExpenseResponseDto getOperationExpenseById(int expenseId) {
        Long userId = getUserData.getUserIdFromToken();
        OperationExpense expense = expenseDao.getOperationExpenseById(userId, expenseId);
        return new OperationExpenseResponseDto(
                expense.getId(),
                expense.getDate(),
                expense.getExpenses_category_name(),
                expense.getAmount()
        );
    }

    public OperationExpenseResponseDto updateOperationExpense(OperationExpenseRequestDto dto, int expenseId) {
        Long userId = getUserData.getUserIdFromToken();
        OperationExpense expense = expenseDao.updateOperationExpense(dto, userId, expenseId);
        return new OperationExpenseResponseDto(
                expense.getId(),
                expense.getDate(),
                expense.getExpenses_category_name(),
                expense.getAmount()
        );
    }

    public List<OperationExpenseResponseDto> getAllOperationExpenses(Optional<String> startDate, Optional<String> endDate) {
        Long userId = getUserData.getUserIdFromToken();
        List<OperationExpense> value = expenseDao.getAllOperationExpenses(userId, startDate, endDate);
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
