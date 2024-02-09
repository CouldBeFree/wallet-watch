package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.UserExpenseStatistic;
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

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<Expense> getUsersExpenses(Long userId) {
        String sql = """
                select expenses_category.id, expenses_category_name from user_expenses_category
                left outer join expenses_category
                on user_expenses_category.expense_category_id = expenses_category.id
                where user_id = ?
                order by id
                """;
        try {
            List<Expense> expenses = jdbcTemplate.query(sql, new ExpenseRowMapper(), userId);
            return expenses;
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public Expense addUserExpense(Long userId, int expenseId) {
        try {
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
            return null;
        } catch (EmptyResultDataAccessException ignore) {
            return null;
        }
    }

    public Expense getExpenseById(Long userID, Object id) {
        String sql = """
                select expenses_category.id, expenses_category_name from user_expenses_category
               left outer join expenses_category
               on user_expenses_category.expense_category_id = expenses_category.id
               where user_id = ? and user_expenses_category.id = ?
               order by id
               """;
        List<Expense> data = jdbcTemplate.query(sql, new ExpenseRowMapper(), userID, id);
        if (data.size() == 0) {
            String notFoundMessage = "Expense " + id + " not found";
            throw new NotFound(notFoundMessage);
        }
        return data.get(0);
    }

    public Object removeUserExpense(Long userId, int expenseId) {
        String sql = """
               delete from user_expenses_category
               where user_id = ? and expense_category_id = ?
               """;
        try {
            return jdbcTemplate.update(sql, userId, expenseId);
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<UserExpenseStatistic> getUsersExpensesTransactionStatisticByPeriod(Long userId, Date startDate, Date endDate){
        String request = """
                SELECT amount, expenses_category.expenses_category_name
                FROM user_transaction_expenses
                JOIN user_expenses_category
                ON user_transaction_expenses.user_id = user_expenses_category.user_id
                JOIN expenses_category
                ON user_expenses_category.expense_category_id = expenses_category.id
                WHERE user_transaction_expenses.user_id = ? AND date between ? and ?
                """;
        try {
            return (List<UserExpenseStatistic>) jdbcTemplate.query(request, new UserExpensesStatisticMapper(), userId, startDate, endDate);
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<UserExpenseStatistic> getUsersExpensesTransactionStatisticByCategory(
            Long userId, Date startDate, Date endDate, List<Integer> categoryId){
//        TODO: this solution doesn't't work userCategoryId should be integers like (1,2,3) now its string;
        StringBuilder sb = new StringBuilder();
        for (Integer id : categoryId) {
            if (Objects.equals(id, categoryId.get(categoryId.size() - 1))){
                sb.append(id);
            }
            else {
            sb.append(id).append(",");
        }}
        String userCategoryId = sb.toString();
        System.out.print(userCategoryId);
        String request = """
                SELECT amount, expenses_category.expenses_category_name
                FROM user_transaction_expenses
                JOIN user_expenses_category
                ON user_transaction_expenses.user_id = user_expenses_category.user_id
                JOIN expenses_category
                ON user_expenses_category.expense_category_id = expenses_category.id
                WHERE user_transaction_expenses.user_id = ?
                    AND user_transaction_expenses.expense_category_id in (?)
                    AND date between ? and ?
                """;
        try {
            return (List<UserExpenseStatistic>) jdbcTemplate.query(request, new UserExpensesStatisticMapper(), userId, userCategoryId, startDate, endDate);
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }
}
