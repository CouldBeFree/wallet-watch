package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.Expense;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        Expense expense = new Expense();
        expense.setId(rs.getLong("id"));
        expense.setExpenses_category_name(rs.getString("expenses_category_name"));
        return expense;
    }
}
