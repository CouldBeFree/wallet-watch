package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationIncomeDao;
import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.dto.OperationIncomeResponseDto;
import com.expenses.walletwatch.entity.OperationIncome;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<OperationIncomeResponseDto> getAllOperationIncomes() {
        Long userId = getUserData.getUserIdFromToken();
        List<OperationIncome> value = incomeDao.getAllOperationExpenses(userId);
        List<OperationIncomeResponseDto> operationIncomeResponseDtos = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            OperationIncome operationIncome = value.get(i);
            OperationIncomeResponseDto operationIncomeResponseDto = new OperationIncomeResponseDto(
                    operationIncome.getId(),
                    operationIncome.getDate(),
                    operationIncome.getIncomes_category_name(),
                    operationIncome.getAmount()
            );
            operationIncomeResponseDtos.add(operationIncomeResponseDto);
        }
        return operationIncomeResponseDtos;
    }

    public OperationIncomeResponseDto updateOperationIncome(OperationIncomeRequestDto dto, int incomeId) {
        Long userId = getUserData.getUserIdFromToken();
        OperationIncome income = incomeDao.updateOperationIncome(dto, userId, incomeId);
        return new OperationIncomeResponseDto(
                income.getId(),
                income.getDate(),
                income.getIncomes_category_name(),
                income.getAmount()
        );
    }
}
