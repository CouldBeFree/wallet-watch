package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.UserExpenseStatistic;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.exception.NotFound;
import com.expenses.walletwatch.mapper.ExpenseRowMapper;
import com.expenses.walletwatch.mapper.UserExpensesStatisticMapper;
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

import static com.expenses.walletwatch.utils.handler.ExpensesByPeriodQueryHandler.GET_EXPENSES_BY_PERIOD;
import static com.expenses.walletwatch.utils.handler.ExpensesByCategoryQueryHandler.QUERY_OPERATION;
import static com.expenses.walletwatch.utils.handler.ExpensesByCategoryQueryHandler.appendQuery;
import static com.expenses.walletwatch.utils.TransformCollectionUtil.flat;

@Repository
public class ExpenseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Expense> getExpensesCategory() {
        String sql = """
                select * from expenses_category
                """;
        try {
            List<Expense> expenses = jdbcTemplate.query(sql, new ExpenseRowMapper());
            return expenses;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<Expense> getUsersExpenses(Long userId) {
        String sql = """
                select user_expenses_category.id, expenses_category_name from user_expenses_category
                left outer join expenses_category
                on user_expenses_category.expense_category_id = expenses_category.id
                where user_id = ? and is_active = true
                order by id
                """;
        try {
            List<Expense> expenses = jdbcTemplate.query(sql, new ExpenseRowMapper(), userId);
            return expenses;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public Expense addUserExpense(Long userId, int expenseId) {
        try {
            int res = getExistingExpenseById(userId, expenseId);
            if (res == 0) {
                GeneratedKeyHolder holder = new GeneratedKeyHolder();
                jdbcTemplate.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement statement = con.prepareStatement("insert into user_expenses_category(user_id, expense_category_id) values(?, ?) ", Statement.RETURN_GENERATED_KEYS);
                        statement.setLong(1, userId);
                        statement.setInt(2, expenseId);
                        return statement;
                    }
                }, holder);
                Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
                if (id.isPresent()) {
                    return getExpenseById(userId, id.get());
                }
            } else {
                try {
                    String sql = """
                        update user_expenses_category
                        set is_active = true
                        where user_id = ? and id = ?
                        """;
                    jdbcTemplate.update(sql, userId, res);
                    return getExpenseById(userId, res);
                } catch (RuntimeException e) {
                    throw new BadRequest(e.getCause());
                }
            }
            return null;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    private int getExistingExpenseById(Long userID, Object id) {
        String sql = """
                select user_expenses_category.id, expenses_category_name, is_active from user_expenses_category
                left outer join expenses_category
                on user_expenses_category.expense_category_id = expenses_category.id
                where user_id = ? and expense_category_id = ?
                order by id;
                """;
        List<Expense> data = jdbcTemplate.query(sql, new ExpenseRowMapper(), userID, id);
        if (data.size() == 0) {
            return 0;
        } else {
            return data.get(0).getId().intValue();
        }
    }

    public Expense getExpenseById(Long userID, Object id) {
        String sql = """
                select user_expenses_category.id, expenses_category_name from user_expenses_category
                left outer join expenses_category
                on user_expenses_category.expense_category_id = expenses_category.id
                where user_id = ? and user_expenses_category.id = ?
                order by id
                """;
        List<Expense> data = jdbcTemplate.query(sql, new ExpenseRowMapper(), userID, id);
        if (data.isEmpty()) {
            String notFoundMessage = "Expense " + id + " not found";
            throw new NotFound(notFoundMessage);
        }
        return data.get(0);
    }

    public Object removeUserExpense(Long userId, int expenseId) {
        String sql = """
                update user_expenses_category
                set is_active = false
                where user_id = ? and id = ?
                """;
        try {
            return jdbcTemplate.update(sql, userId, expenseId);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<UserExpenseStatistic> getUsersExpensesTransactionStatisticByPeriod(Long userId, String startDate, String endDate) {
        try {
            return (List<UserExpenseStatistic>) jdbcTemplate.query(GET_EXPENSES_BY_PERIOD, new UserExpensesStatisticMapper(), userId, startDate, endDate);
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public List<UserExpenseStatistic> getUsersExpensesTransactionStatisticByCategory(
            Long userId, Date startDate, Date endDate, List<Integer> categoryId) {
        String request = categoryId == null || categoryId.isEmpty()
                ? QUERY_OPERATION
                : String.format(appendQuery(), String.join(", ", Collections.nCopies(categoryId.size(), "?")));

        try {
            return (List<UserExpenseStatistic>) jdbcTemplate.query
                    (request,
                            new UserExpensesStatisticMapper(), flat(List.of(List.of(userId, startDate, endDate), categoryId)));
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
