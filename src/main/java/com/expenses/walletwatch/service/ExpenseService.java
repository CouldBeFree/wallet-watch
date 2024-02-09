package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.ExpenseDao;
import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.UserExpenseStatistic;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseDao expenseDao;
    private final GetUserData getUserData;

    public ExpenseService(ExpenseDao expenseDao, GetUserData getUserData) {

        this.expenseDao = expenseDao;
        this.getUserData = getUserData;
    }

    public List<Expense> getExpensesCategories() {
        return expenseDao.getExpensesCategory();
    }

    public List<Expense> createUserExpense(ExpenseRequestDto dto) {
        try {
            // TODO: replace user_id with id from token
            return expenseDao.addUserExpense(6, dto.getExpense_id());
        } catch (DuplicateKeyException e) {
            throw new BadRequest("Category already added");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequest("Expense category does not exists");
        }
    }

    public List<Expense> deleteUserExpense(ExpenseRequestDto dto) {
        try {
            // TODO: replace user_id with id from token
            return expenseDao.removeUserExpense(6, dto.getExpense_id());
        } catch (RuntimeException e) {
            throw new BadRequest("Something went wrong");
        }
    }

    public List<Expense> getAllUsersExpenses() {
        return expenseDao.getUsersExpenses(6);
    }

    public List<UserExpenseStatistic> userTransactionsStatisticForPeriod(Date startDate, Date endDate) {
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpensesTransactionStatisticByPeriod(userId, startDate, endDate);
    }

    public List<UserExpenseStatistic> userTransactionsStatisticByCategory(List<Integer> categoryId, Date startDate, Date endDate){
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpensesTransactionStatisticByCategory(userId, startDate, endDate, categoryId);
    }

}
