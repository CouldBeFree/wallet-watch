package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.TotalDao;
import com.expenses.walletwatch.dto.TotalDto;
import com.expenses.walletwatch.entity.TotalExpense;
import com.expenses.walletwatch.entity.TotalIncome;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

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
}
