package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.entity.OperationIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationIncomeRowMapper implements RowMapper {
    @Override
    public OperationIncome mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperationIncome operationIncome = new OperationIncome();
        operationIncome.setId(rs.getLong("id"));
        operationIncome.setDate(rs.getString("date"));
        operationIncome.setIncomes_category_name(rs.getString("incomes_category_name"));
        operationIncome.setAmount(rs.getInt("amount"));

        return operationIncome;
    }
}
