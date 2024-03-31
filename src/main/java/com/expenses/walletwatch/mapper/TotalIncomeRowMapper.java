package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.TotalIncome;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalIncomeRowMapper implements RowMapper {
    @Override
    public TotalIncome mapRow(ResultSet rs, int rowNum) throws SQLException {
        TotalIncome totalIncome = new TotalIncome();
        totalIncome.setTotal_income(rs.getLong("total_income"));

        return totalIncome;
    }
}
