package com.expenses.walletwatch.mapper;
import com.expenses.walletwatch.entity.Income;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeRowMapper implements RowMapper {

    @Override
    public Income mapRow(ResultSet rs, int rowNum) throws SQLException {
        Income income = new Income();
        income.setId(rs.getLong("id"));
        income.setIncomes_category_name(rs.getString("incomes_category_name"));
        return income;
    }
}
