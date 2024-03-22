package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.OperationIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationIncomeRowMapperID implements RowMapper {
    @Override
    public OperationIncome mapRow(ResultSet rs, int rowNum) throws SQLException {
        OperationIncome operationIncome = new OperationIncome();
        operationIncome.setId(rs.getLong("id"));

        return operationIncome;
    }
}
