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
}
