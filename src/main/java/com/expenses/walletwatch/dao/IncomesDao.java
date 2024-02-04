package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.Income;
import com.expenses.walletwatch.mapper.IncomeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Income> addUserIncome(int userId, int incomeId) {
        String sql = """
                insert into user_incomes_category(user_id, income_category_id)
                values(?, ?);
                """;
        try {
            jdbcTemplate.update(sql, userId, incomeId);
            return getUsersIncomes(userId);
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }

    public List<Income> getUsersIncomes(int userId) {
        String sql = """
                select incomes_category.id, incomes_category_name, user_id from user_incomes_category
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

    public List<Income> removeUserIncome(int userId, int incomeId) {
        String sql = """
               delete from user_incomes_category
               where user_id = ? and income_category_id = ?
               """;
        try {
            jdbcTemplate.update(sql, userId, incomeId);
            return getUsersIncomes(userId);
        } catch (EmptyResultDataAccessException ignore) {return null;}
    }
}
