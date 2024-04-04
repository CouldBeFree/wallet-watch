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

    public String deleteUserExpense(int categoryId) {
        try {
            Long userId = getUserData.getUserIdFromToken();
            expenseDao.removeUserExpense(userId, categoryId);
            return "Removed";
        } catch (RuntimeException e) {
            throw new BadRequest("Something went wrong");
        }
    }

    public List<Expense> getAllUsersExpenses() {
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpenses(userId);
    }

    public List<UserExpenseStatistic> userTransactionsStatisticForPeriod(String startDate, String endDate) {
//        TODO: add startDate and endDate validator, if startDate is empty need to get user registration date if endDate is empty - today date
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpensesTransactionStatisticByPeriod(userId, startDate, endDate);
    }

    public List<UserExpenseStatistic> userTransactionsStatisticByCategory(List<Integer> categoryId, Date startDate, Date endDate){
//        TODO: add startDate and endDate validator, if startDate is empty need to get user registration date if endDate is empty - today date
        Long userId = getUserData.getUserIdFromToken();
        return expenseDao.getUsersExpensesTransactionStatisticByCategory(userId, startDate, endDate, categoryId);
    }

}
