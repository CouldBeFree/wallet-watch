package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.IncomesDao;
import com.expenses.walletwatch.entity.Income;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private final IncomesDao incomeDao;

    public IncomeService(IncomesDao incomeDao) {
        this.incomeDao = incomeDao;
    }

    public List<Income> getAllIncomes() {
        return incomeDao.getIncomesCategory();
    }
}
