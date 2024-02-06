package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.ExpenseDao;
import com.expenses.walletwatch.dao.OperationExpenseDao;
import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
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
}
