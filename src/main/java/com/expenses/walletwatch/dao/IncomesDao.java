package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.mapper.ExpenseRowMapper;
import com.expenses.walletwatch.mapper.IncomeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomesDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IncomesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Income> getIncomesCategory() {
        String sql = """
                select * from incomes_category
                """;
        try {
            List<Income> incomes = jdbcTemplate.query(sql, new IncomeRowMapper());
            return incomes;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }
}
