package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.dto.OperationExpenseRequestDto;
import com.expenses.walletwatch.entity.OperationExpense;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.exception.NotFound;
import com.expenses.walletwatch.mapper.OperationExpenseRowMapper;
import com.expenses.walletwatch.mapper.OperationExpenseRowMapperID;
import com.expenses.walletwatch.utils.DateFormatParser;
import com.expenses.walletwatch.utils.GetIdFromCreatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static com.expenses.walletwatch.utils.handler.TransactionsExpenseQueryHandler.*;

@Repository
public class OperationExpenseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OperationExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OperationExpense createOperationExpense(OperationExpenseRequestDto dto, Long userId) {
        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into user_transaction_expenses(amount, user_id, expense_category_id, date) values(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getAmount());
                    statement.setLong(2, userId);
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

    public OperationExpense updateOperationExpense(OperationExpenseRequestDto dto, Long userId, int expenseId) {
        try {
            getOperationExpenseById(userId, expenseId);
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("update user_transaction_expenses set amount = ?, expense_category_id = ?, date = ? where id = ? and user_id = ?", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getAmount());
                    statement.setLong(2, getExpenseCategoryIdByName(dto.getExpenses_category_name(), userId));
                    statement.setDate(3, DateFormatParser.ConvertDate(dto.getDate()));
                    statement.setInt(4, expenseId);
                    statement.setLong(5, userId);
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getOperationExpenseById(userId, id.get());
            }
            return null;
        } catch (RuntimeException e) {
            return null;
        }
    }

    private Long getExpenseCategoryIdByName(String name, Long userId) {
        String sql = """
               select user_expenses_category.id from user_expenses_category
               right outer join expenses_category
               on user_expenses_category.expense_category_id = expenses_category.id
               where expenses_category.expenses_category_name = ?
               and user_id = ?;
               """;
        List<OperationExpense> operationExpenses = jdbcTemplate.query(sql, new OperationExpenseRowMapperID(), name, userId);
        return operationExpenses.get(0).getId();
    }

    public OperationExpense getOperationExpenseById(Long userId, Object id) {
        String sql = """
                select user_transaction_expenses.id, amount, date, expenses_category_name from user_transaction_expenses
                left outer join expenses_category
                on user_transaction_expenses.expense_category_id = expenses_category.id
                where user_id = ? and user_transaction_expenses.id = ?;
                """;
        try {
            List<OperationExpense> data = jdbcTemplate.query(sql, new OperationExpenseRowMapper(), userId, id);
            if (data.size() == 0) {
                String notFoundMessage = "Expense " + id + " not found";
                throw new NotFound(notFoundMessage);
            }
            return data.get(0);
        } catch (EmptyResultDataAccessException e) {
            //TODO: update to NotFound
            throw new BadRequest(e.getCause());
        }
    }

    public boolean removeOperationExpense(Long userId, int id) {
        String sql = """
               delete from user_transaction_expenses
               where user_id = ? and id = ?
               """;
        try {
            jdbcTemplate.update(sql,userId, id);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new BadRequest(e.getMessage());
        }
    };

    public List<OperationExpense> getAllOperationExpenses(Long userId, Optional<String> startDate, Optional<String> endDate) {
        String request = startDate.isEmpty() && endDate.isEmpty() ? GET_EXPENSES_TRANSACTIONS + ORDER_BY_ID : appendQuery() + ORDER_BY_ID;
        try {
            if (startDate.isPresent() && endDate.isPresent()) {
                return jdbcTemplate.query(request, new OperationExpenseRowMapper(), userId, startDate.get(), endDate.get());
            } else {
                return jdbcTemplate.query(request, new OperationExpenseRowMapper(), userId);
            }
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
