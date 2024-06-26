package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.OperationIncomeDao;
import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.dto.OperationIncomeResponseDto;
import com.expenses.walletwatch.entity.OperationIncome;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public String removeOperationIncome(int incomeId) {
        Long userId = getUserData.getUserIdFromToken();
        try {
            incomeDao.removeOperationIncome(userId, incomeId);
            return "Removed";
        } catch (EmptyResultDataAccessException e) {
            throw new BadRequest(e.getCause());
        }
    }

    public List<OperationIncomeResponseDto> getAllOperationIncomes(Optional<String> startDate, Optional<String> endDate) {
        Long userId = getUserData.getUserIdFromToken();
        List<OperationIncome> value = incomeDao.getAllOperationExpenses(userId, startDate, endDate);
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
