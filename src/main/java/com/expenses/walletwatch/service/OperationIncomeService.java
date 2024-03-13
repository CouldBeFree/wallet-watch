package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationIncomeDao;
import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.dto.OperationIncomeResponseDto;
import com.expenses.walletwatch.entity.OperationIncome;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

@Service
public class OperationIncomeService {
    private final OperationIncomeDao incomeDao;
    private final GetUserData getUserData;

    public OperationIncomeService(OperationIncomeDao incomeDao, GetUserData getUserData) {
        this.incomeDao = incomeDao;
        this.getUserData = getUserData;
    }

    public OperationIncomeResponseDto createOperationIncome(OperationIncomeRequestDto dto) {
        Long userId = getUserData.getUserIdFromToken();
        try {
            OperationIncome operationIncome = incomeDao.createOperationExpense(dto, userId);
            return new OperationIncomeResponseDto(
                    operationIncome.getId(),
                    operationIncome.getDate(),
                    operationIncome.getIncomes_category_name(),
                    operationIncome.getAmount()
            );
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
