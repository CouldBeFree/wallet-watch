package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.ExpenseDao;
import com.expenses.walletwatch.entity.Expense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;

    public ExpenseService(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public List<Expense> getExpensesCategories() {
        return expenseDao.getExpensesCategory();
    }
}
