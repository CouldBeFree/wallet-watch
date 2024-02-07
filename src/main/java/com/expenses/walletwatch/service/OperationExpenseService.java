package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationExpenseDao;
import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.dto.OperationExpenseResponseDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationExpenseService {
    private final OperationExpenseDao expenseDao;

    public OperationExpenseService(OperationExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public OperationExpense createOperationExpense(OperationExpenseRequestDto dto) {
        try {
            return expenseDao.createOperationExpense(dto, 6);
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

    public List<OperationExpense> getAllOperationExpenses() {
        return expenseDao.getAllOperationExpenses(6);
    }
}
