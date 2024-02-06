package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.OperationExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationExpenseRowMapper implements RowMapper {

    @Override
    public OperationExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperationExpense operationExpense = new OperationExpense();
        operationExpense.setId(rs.getLong("id"));
        operationExpense.setExpense_category_id(rs.getString("expense_category_id"));
        operationExpense.setDate(rs.getString("date"));
        operationExpense.setAmount(rs.getInt("amount"));
        operationExpense.setUser_id(rs.getInt("user_id"));

        return operationExpense;
    }
}
