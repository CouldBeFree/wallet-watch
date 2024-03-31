package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.TotalExpense;
import com.expenses.walletwatch.entity.TotalIncome;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.TotalExpenseRowMapper;
import com.expenses.walletwatch.mapper.TotalIncomeRowMapper;
import com.expenses.walletwatch.utils.DateFormatParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TotalDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TotalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TotalExpense getExpense(Long userId, Optional<String> startDate, Optional<String> endDate) {
        String sqlWithDate = """
                select sum(amount) as total_expense from user_transaction_expenses
                where user_id = ? and date between ? and ?
                """;
        String rawSql = """
                select sum(amount) as total_expense from user_transaction_expenses
                where user_id = ?
                """;
        String request = startDate.isEmpty() && endDate.isEmpty() ? rawSql : sqlWithDate;
        try {
            if (startDate.isPresent() && endDate.isPresent()) {
                List<TotalExpense> totalExpenseItem = jdbcTemplate.query(request, new TotalExpenseRowMapper(), userId, DateFormatParser.ConvertDate(startDate.get()), DateFormatParser.ConvertDate(endDate.get()));
                return totalExpenseItem.get(0);
            } else {
                List<TotalExpense> totalExpenseItem = jdbcTemplate.query(request, new TotalExpenseRowMapper(), userId);
                return totalExpenseItem.get(0);
            }
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    public TotalIncome getIncome(Long userId, Optional<String> startDate, Optional<String> endDate) {
        String sqlWithDate = """
                select sum(amount) as total_income from user_transaction_incomes
                where user_id = ? and date between ? and ?
                """;
        String rawSql = """
                select sum(amount) as total_income from user_transaction_incomes
                where user_id = ?
                """;
        String request = startDate.isEmpty() && endDate.isEmpty() ? rawSql : sqlWithDate;
        try {
            if (startDate.isPresent() && endDate.isPresent()) {
                List<TotalIncome> totalIncomeItem = jdbcTemplate.query(request, new TotalIncomeRowMapper(), userId, DateFormatParser.ConvertDate(startDate.get()), DateFormatParser.ConvertDate(endDate.get()));
                return totalIncomeItem.get(0);
            } else {
                List<TotalIncome> totalIncomeItem = jdbcTemplate.query(request, new TotalIncomeRowMapper(), userId);
                return totalIncomeItem.get(0);
            }
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
    }
}
