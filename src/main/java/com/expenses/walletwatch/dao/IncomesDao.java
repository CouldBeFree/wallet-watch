package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.entity.UserExpenseStatistic;
import com.expenses.walletwatch.entity.UserIncomeStatistic;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.exception.NotFound;
import com.expenses.walletwatch.mapper.IncomeRowMapper;
import com.expenses.walletwatch.mapper.UserIncomesStatisticMapper;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.expenses.walletwatch.utils.TransformCollectionUtil.flat;
import static com.expenses.walletwatch.utils.handler.IncomesByCategoryQueryHandler.appendQuery;
import static com.expenses.walletwatch.utils.handler.IncomesByCategoryQueryHandler.GET_INCOMES_BY_CATEGORY;
import static com.expenses.walletwatch.utils.handler.IncomesByPeriodQueryHandler.GET_INCOMES_BY_PERIOD;


@Repository
public class IncomesDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public IncomesDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Income> getIncomesCategory() {
        String sql = """
                select * from incomes_category
                """;
        try {
            List<Income> incomes = jdbcTemplate.query(sql, new IncomeRowMapper());
            return incomes;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public Income addUserIncome(Long userId, int incomeId) {
        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into user_incomes_category(user_id, income_category_id) values(?, ?) ", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, userId);
                    statement.setInt(2, incomeId);
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getIncomeById(userId, id.get());
            }
            return null;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public Income getIncomeById(Long userId, Object id) {
        String sql = """
                select user_incomes_category.id, incomes_category.incomes_category_name from user_incomes_category
                 left outer join incomes_category
                 on user_incomes_category.income_category_id = incomes_category.id
                 where user_id = ? and user_incomes_category.id = ?
               """;
        List<Income> data = jdbcTemplate.query(sql, new IncomeRowMapper(), userId, id);
        if (data.size() == 0) {
            String notFoundMessage = "Income " + id + " not found";
            throw new NotFound(notFoundMessage);
        }
        return data.get(0);
    }

    public List<Income> getUsersIncomes(Long userId) {
        String sql = """
                select user_incomes_category.id, incomes_category_name, user_id from user_incomes_category
                left outer join incomes_category
                on user_incomes_category.income_category_id = incomes_category.id
                where user_id = ?;
                """;
        try {
            List<Income> incomes = jdbcTemplate.query(sql, new IncomeRowMapper(), userId);
            return incomes;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public Object removeUserIncome(Long userId, int incomeId) {
        String sql = """
               delete from user_incomes_category
               where user_id = ? and id = ?
               """;
        try {
            return jdbcTemplate.update(sql, userId, incomeId);
        } catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<UserIncomeStatistic> getUsersIncomesTransactionStatisticByPeriod(Long userId, Date startDate, Date endDate) {
        try {
            return (List<UserIncomeStatistic>) jdbcTemplate.query(GET_INCOMES_BY_PERIOD, new UserIncomesStatisticMapper(), userId, startDate, endDate);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<UserIncomeStatistic> getUsersIncomesTransactionStatisticByCategory(Long userId, Date startDate, Date endDate, List<Integer> categoryId) {
        String request = categoryId == null || categoryId.isEmpty()
                ? GET_INCOMES_BY_CATEGORY
                : String.format(appendQuery(), String.join(", ", Collections.nCopies(categoryId.size(), "?")));

        try {
            return (List<UserIncomeStatistic>) jdbcTemplate.query
                    (request,
                            new UserIncomesStatisticMapper(), flat(List.of(List.of(userId, startDate, endDate), categoryId)));
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
