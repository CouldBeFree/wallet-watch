package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.OperationExpenseRowMapper;
import com.expenses.walletwatch.utils.DateFormatParser;
import com.expenses.walletwatch.utils.GetIdFromCreatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class OperationExpenseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OperationExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OperationExpense createOperationExpense(OperationExpenseRequestDto dto, int userId) {
        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into user_transaction_expenses(amount, user_id, expense_category_id, date) values(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getAmount());
                    statement.setInt(2, userId);
                    statement.setInt(3, dto.getExpense_category_id());
                    statement.setDate(4, DateFormatParser.ConvertDate(dto.getDate()));
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getOperationExpenseById(userId, id.get());
            }
            return null;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public OperationExpense getOperationExpenseById(int userId, Object id) {
        String sql = """
                select * from user_transaction_expenses
                where user_id = ? and id = ?
                """;
        try {
            List<OperationExpense> data = jdbcTemplate.query(sql, new OperationExpenseRowMapper(), userId, id);
            return data.get(0);
        } catch (EmptyResultDataAccessException e) {
            //TODO: update to NotFound
            throw new BadRequest(e.getCause());
        }
    }

    public List<OperationExpense> getOperationExpenses(int userId) {
        String sql = """
                select * from user_transaction_expenses
                where user_id = ?
                """;
        try {
            return jdbcTemplate.query(sql, new OperationExpenseRowMapper(), userId);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }
}
