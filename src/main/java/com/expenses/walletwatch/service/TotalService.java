package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.TotalDao;
import com.expenses.walletwatch.dto.TotalDto;
import com.expenses.walletwatch.dto.TransactionHistoryDto;
import com.expenses.walletwatch.entity.TotalExpense;
import com.expenses.walletwatch.entity.TotalIncome;
import com.expenses.walletwatch.entity.TransactionHistory;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TotalService {
    private final TotalDao totalDao;
    private final GetUserData getUserData;

    public TotalService(TotalDao totalDao, GetUserData getUserData) {
        this.totalDao = totalDao;
        this.getUserData = getUserData;
    }

    public TotalDto getTotal(Optional<String> startDate, Optional<String> endDate) {
        Long userId = getUserData.getUserIdFromToken();
        TotalExpense totalExpense = totalDao.getExpense(userId, startDate, endDate);
        TotalIncome totalIncome = totalDao.getIncome(userId, startDate, endDate);
        return new TotalDto(totalExpense.getTotal_expense(), totalIncome.getTotal_income());
    }

    public List<TransactionHistoryDto> getHistory(Optional<String> startDate, Optional<String> endDate) {
        List<TransactionHistoryDto> transactionHistoryDtos = new ArrayList<>();
        Long userId = getUserData.getUserIdFromToken();
        List<TransactionHistory> transactionHistory = totalDao.getTransactionhistory(userId, startDate, endDate);
        for (int i = 0; i < transactionHistory.size(); i++) {
            TransactionHistory transactionHistoryItem = transactionHistory.get(i);
            TransactionHistoryDto dto = new TransactionHistoryDto(
                    transactionHistoryItem.getId(),
                    transactionHistoryItem.getAmount(),
                    transactionHistoryItem.getExpenses(),
                    transactionHistoryItem.getDate()
            );
            transactionHistoryDtos.add(dto);
        }
        return transactionHistoryDtos;
    }
}
