package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.ExpenseDao;
import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.dao.DuplicateKeyException;
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

    public List<Expense> getUserExpensesCategories(ExpenseRequestDto dto) {
        try {
            //TODO: replace user_id with id from token
            return expenseDao.addUserExpense(36, dto.getExpense_id());
        } catch (DuplicateKeyException e) {
            throw new BadRequest("Category already added");
        }
    }
}
