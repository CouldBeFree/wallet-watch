package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.TotalExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalExpenseRowMapper implements RowMapper {
    @Override
    public TotalExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
        TotalExpense totalExpense = new TotalExpense();
        totalExpense.setTotal_expense(rs.getLong("total_expense"));

        return totalExpense;
    }
}
