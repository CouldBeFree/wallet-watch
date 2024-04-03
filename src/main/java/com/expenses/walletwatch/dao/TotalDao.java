package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.TotalExpense;
import com.expenses.walletwatch.entity.TotalIncome;
import com.expenses.walletwatch.entity.TransactionHistory;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.TotalExpenseRowMapper;
import com.expenses.walletwatch.mapper.TotalIncomeRowMapper;
import com.expenses.walletwatch.mapper.TransactionHistoryMapper;
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

    public List<TransactionHistory> getTransactionhistory(Long userId, Optional<String> startDate, Optional<String> endDate) {
        String sqlWithDate = """
                SELECT *
                FROM (
                    SELECT
                        user_transaction_expenses.id AS id,
                        user_transaction_expenses.date AS transaction_date,
                        user_transaction_expenses.amount AS amount,
                        user_transaction_expenses.user_id AS user,
                        user_transaction_expenses.expense_category_id AS category_id,
                        expenses_category.expenses_category_name AS category_name,
                        true AS expenses
                    FROM user_transaction_expenses
                    LEFT OUTER JOIN user_expenses_category ON user_transaction_expenses.expense_category_id = user_expenses_category.id
                    LEFT OUTER JOIN expenses_category ON user_expenses_category.expense_category_id = expenses_category.id
                   \s
                    UNION
                   \s
                    SELECT
                        user_transaction_incomes.id AS id,
                        user_transaction_incomes.date AS transaction_date,
                        user_transaction_incomes.amount AS amount,
                        user_transaction_incomes.user_id AS user,
                        user_transaction_incomes.income_category_id AS category_id,
                        incomes_category.incomes_category_name AS category_name,
                        false AS expenses
                    FROM user_transaction_incomes
                    LEFT OUTER JOIN user_incomes_category ON user_transaction_incomes.income_category_id = user_incomes_category.id
                    LEFT OUTER JOIN incomes_category ON user_incomes_category.income_category_id = incomes_category.id
                ) AS test_table
                WHERE test_table.user = ?
                and test_table.transaction_date between ? and ?
                ORDER BY transaction_date desc
                LIMIT 4
                """;
        String rawSql = """
                SELECT *
                FROM (
                    SELECT
                        user_transaction_expenses.id AS id,
                        user_transaction_expenses.date AS transaction_date,
                        user_transaction_expenses.amount AS amount,
                        user_transaction_expenses.user_id AS user,
                        user_transaction_expenses.expense_category_id AS category_id,
                        expenses_category.expenses_category_name AS category_name,
                        true AS expenses
                    FROM user_transaction_expenses
                    LEFT OUTER JOIN user_expenses_category ON user_transaction_expenses.expense_category_id = user_expenses_category.id
                    LEFT OUTER JOIN expenses_category ON user_expenses_category.expense_category_id = expenses_category.id
                   \s
                    UNION
                   \s
                    SELECT
                        user_transaction_incomes.id AS id,
                        user_transaction_incomes.date AS transaction_date,
                        user_transaction_incomes.amount AS amount,
                        user_transaction_incomes.user_id AS user,
                        user_transaction_incomes.income_category_id AS category_id,
                        incomes_category.incomes_category_name AS category_name,
                        false AS expenses
                    FROM user_transaction_incomes
                    LEFT OUTER JOIN user_incomes_category ON user_transaction_incomes.income_category_id = user_incomes_category.id
                    LEFT OUTER JOIN incomes_category ON user_incomes_category.income_category_id = incomes_category.id
                ) AS test_table
                WHERE test_table.user = ?
                ORDER BY transaction_date desc
                LIMIT 4
                """;
        String request = startDate.isEmpty() && endDate.isEmpty() ? rawSql : sqlWithDate;
        try {
            if (startDate.isPresent() && endDate.isPresent()) {
                return jdbcTemplate.query(request, new TransactionHistoryMapper(), userId, DateFormatParser.ConvertDate(startDate.get()), DateFormatParser.ConvertDate(endDate.get()));
            } else {
                return jdbcTemplate.query(request, new TransactionHistoryMapper(), userId);
            }
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
