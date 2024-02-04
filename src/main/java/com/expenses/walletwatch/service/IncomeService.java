package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.IncomesDao;
import com.expenses.walletwatch.dto.IncomeRequestDto;
import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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

    public List<Income> createUserIncome(IncomeRequestDto dto) {
        try {
            // TODO: replace user_id with id from token
            return incomeDao.addUserIncome(6, dto.getIncome_id());
        } catch (DuplicateKeyException e) {
            throw new BadRequest("Income already added");
        } catch (DataIntegrityViolationException e) {
            throw new BadRequest("Expense category does not exists");
        }
    }
}
