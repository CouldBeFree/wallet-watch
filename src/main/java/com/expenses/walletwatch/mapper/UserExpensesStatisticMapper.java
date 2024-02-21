package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.UserExpenseStatistic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExpensesStatisticMapper implements RowMapper<UserExpenseStatistic> {
    @Override
    public UserExpenseStatistic mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserExpenseStatistic userExpenseStatistic = new UserExpenseStatistic();
        userExpenseStatistic.setExpenses_category_name(rs.getString("expenses_category_name"));
        userExpenseStatistic.setAmount_sum(rs.getFloat("sum"));
        userExpenseStatistic.setId(rs.getInt("expense_category_id"));
        return userExpenseStatistic;
    }
}
