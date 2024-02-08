package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.ExpenseDao;
import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;
    private final GetUserData getUserData;

    public ExpenseService(ExpenseDao expenseDao, GetUserData getUserData) {
        this.getUserData = getUserData;
        this.expenseDao = expenseDao;
    }

    public List<Expense> getExpensesCategories() {
        return expenseDao.getExpensesCategory();
    }

    public Expense createUserExpense(ExpenseRequestDto dto) {
        try {
            Long userId = getUserData.getUserIdFromToken();
            return expenseDao.addUserExpense(userId, dto.getExpense_id());
        } catch (DuplicateKeyException e) {
            throw new BadRequest("Category already added");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequest("Expense category does not exists");
        }
    }

    public List<Expense> deleteUserExpense(ExpenseRequestDto dto) {
        try {
            Long userId = getUserData.getUserIdFromToken();
            return expenseDao.removeUserExpense(userId, dto.getExpense_id());
        } catch (RuntimeException e) {
            throw new BadRequest("Something went wrong");
        }
    }

    public List<Expense> getAllUsersExpenses() {
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpenses(userId);
    }
}
