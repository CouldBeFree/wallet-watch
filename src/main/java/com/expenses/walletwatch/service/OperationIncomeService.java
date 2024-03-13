package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationIncomeDao;
import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.dto.OperationIncomeResponseDto;
import org.springframework.stereotype.Service;

@Service
public class OperationIncomeService {
    private final OperationIncomeDao incomeDao;

    public OperationIncomeService(OperationIncomeDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public OperationIncomeResponseDto createOperationIncome(OperationIncomeRequestDto dto) {
        return new OperationIncomeResponseDto(500, "2021-04-10", "Category", 10);
    }
}
