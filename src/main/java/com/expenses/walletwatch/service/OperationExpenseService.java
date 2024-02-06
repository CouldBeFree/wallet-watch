package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationExpenseDao;
import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

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

    public String removeOperationExpense(OperationExpenseRequestDto dto) {
        try {
            expenseDao.removeOperationExpense(6, dto.getExpense_category_id());
            return "Removed";
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
