package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.TransactionHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionHistoryMapper implements RowMapper {
    @Override
    public TransactionHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(rs.getLong("id"));
        transactionHistory.setDate(rs.getString("transaction_date"));
        transactionHistory.setAmount(rs.getLong("amount"));
        transactionHistory.setExpenses(rs.getBoolean("expenses"));

        return transactionHistory;
    }
}
