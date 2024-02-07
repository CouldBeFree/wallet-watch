package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.OperationExpense;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationExpenseRowMapperID implements RowMapper {
    @Override
    public OperationExpense mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperationExpense operationExpense = new OperationExpense();
        operationExpense.setId(rs.getLong("id"));

        return operationExpense;
    }
}
