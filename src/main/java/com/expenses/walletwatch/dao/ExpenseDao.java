package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.mapper.ExpenseRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExpenseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Expense> getExpensesCategory() {
        String sql = """
                select * from expenses_category
                """;
        try {
            List<Expense> expenses = jdbcTemplate.query(sql, new ExpenseRowMapper());
            return expenses;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<Expense> getUsersExpenses(int userId) {
        String sql = """
                select expenses_category.id, expenses_category_name from user_expenses_category
                left outer join expenses_category
                on user_expenses_category.expense_category_id = expenses_category.id
                where user_id = ?
                """;
        try {
            List<Expense> expenses = jdbcTemplate.query(sql, new ExpenseRowMapper(), userId);
            return expenses;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<Expense> addUserExpense(int userId, int expenseId) {
        String sql = """
                insert into user_expenses_category(user_id, expense_category_id)
                values(?, ?);
                """;
        try {
            jdbcTemplate.update(sql, userId, expenseId);
            List<Expense> expenses = getUsersExpenses(userId);
            return expenses;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }
}
