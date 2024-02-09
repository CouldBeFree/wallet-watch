package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.IncomesDao;
import com.expenses.walletwatch.dto.IncomeRequestDto;
import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    private final IncomesDao incomeDao;
    private final GetUserData getUserData;

    public IncomeService(IncomesDao incomeDao, GetUserData getUserData) {
        this.getUserData = getUserData;
        this.incomeDao = incomeDao;
    }

    public List<Income> getAllIncomes() {
        return incomeDao.getIncomesCategory();
    }

    public Income createUserIncome(IncomeRequestDto dto) {
        try {
            Long userId = getUserData.getUserIdFromToken();
            return incomeDao.addUserIncome(userId, dto.getIncome_id());
        } catch (DuplicateKeyException e) {
            throw new BadRequest("Income already added");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequest("Income category does not exists");
        }
    }

    public String removeUserIncome(int incomeId) {
        Long userId = getUserData.getUserIdFromToken();
        try {
            incomeDao.removeUserIncome(userId, incomeId);
            return "Income removed";
        } catch (RuntimeException e) {
            throw new BadRequest("Something went wrong");
        }
    }

    public List<Income> getAllUserIncomes() {
        Long userId = getUserData.getUserIdFromToken();
        return incomeDao.getUsersIncomes(userId);
    }
}
