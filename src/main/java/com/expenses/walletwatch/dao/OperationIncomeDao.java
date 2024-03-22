package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.dto.OperationIncomeRequestDto;
import com.expenses.walletwatch.entity.OperationIncome;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.exception.NotFound;
import com.expenses.walletwatch.mapper.OperationIncomeRowMapper;
import com.expenses.walletwatch.mapper.OperationIncomeRowMapperID;
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
public class OperationIncomeDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OperationIncomeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public OperationIncome createOperationExpense(OperationIncomeRequestDto dto, Long userId) {
        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into user_transaction_incomes(amount, user_id, income_category_id, date) values(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getAmount());
                    statement.setLong(2, userId);
                    statement.setInt(3, dto.getIncome_category_id());
                    statement.setDate(4, DateFormatParser.ConvertDate(dto.getDate()));
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getOperationIncomeById(userId, id.get());
            }
            return null;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public OperationIncome getOperationIncomeById(Long userId, Object id) {
        String sql = """
                select user_transaction_incomes.id, amount, incomes_category.incomes_category_name, user_transaction_incomes.date
                from user_transaction_incomes
                left outer join user_incomes_category
                on user_incomes_category.id = user_transaction_incomes.income_category_id
                left outer join incomes_category
                on user_incomes_category.income_category_id = incomes_category.id
                where user_transaction_incomes.user_id = ? and user_transaction_incomes.id = ?;
                """;
        try {
            List<OperationIncome> data = jdbcTemplate.query(sql, new OperationIncomeRowMapper(), userId, id);
            if (data.size() == 0) {
                String notFoundMessage = "Income " + id + " not found";
                throw new NotFound(notFoundMessage);
            }
            return data.get(0);
        } catch (EmptyResultDataAccessException e) {
            //TODO: update to NotFound
            throw new BadRequest(e.getCause());
        }
    }

    public List<OperationIncome> getAllOperationExpenses(Long userId) {
        String sql = """
                select user_transaction_incomes.id, amount, date, incomes_category.incomes_category_name from user_transaction_incomes
                left outer join user_incomes_category
                on user_transaction_incomes.income_category_id = user_incomes_category.id
                left outer join incomes_category
                on user_incomes_category.income_category_id = incomes_category.id
                where user_incomes_category.user_id = ?
                order by id;
                """;
        try {
            return jdbcTemplate.query(sql, new OperationIncomeRowMapper(), userId);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public OperationIncome updateOperationIncome(OperationIncomeRequestDto dto, Long userId, int incomeId) {
        try {
            getOperationIncomeById(userId, incomeId);
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("update user_transaction_incomes set amount = ?, income_category_id = ?, date = ? where id = ? and user_id = ?", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getAmount());
                    statement.setLong(2, getIncomeCategoryIdByName(dto.getIncomes_category_name(), userId));
                    statement.setDate(3, DateFormatParser.ConvertDate(dto.getDate()));
                    statement.setInt(4, incomeId);
                    statement.setLong(5, userId);
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getOperationIncomeById(userId, id.get());
            }
            return null;
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }

    private Long getIncomeCategoryIdByName(String name, Long userId) {
        String sql = """
               select user_incomes_category.id from user_incomes_category
               right outer join incomes_category
               on user_incomes_category.income_category_id = incomes_category.id
               where incomes_category.incomes_category_name = ?
               and user_id = ?;
               """;
        List<OperationIncome> operationIncomes = jdbcTemplate.query(sql, new OperationIncomeRowMapperID(), name, userId);
        return operationIncomes.get(0).getId();
    }
}
