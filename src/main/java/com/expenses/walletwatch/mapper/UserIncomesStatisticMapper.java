package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.UserExpenseStatistic;
import com.expenses.walletwatch.entity.UserIncomeStatistic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserIncomesStatisticMapper implements RowMapper<UserIncomeStatistic> {

    @Override
    public UserIncomeStatistic mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserIncomeStatistic userIncomeStatistic = new UserIncomeStatistic();
        userIncomeStatistic.setIncome_category_name(rs.getString("incomes_category_name"));
        userIncomeStatistic.setAmount_sum(rs.getFloat("sum"));
        userIncomeStatistic.setId(rs.getInt("income_category_id"));
        userIncomeStatistic.setColor(rs.getString("color"));
        return userIncomeStatistic;
    }
}
