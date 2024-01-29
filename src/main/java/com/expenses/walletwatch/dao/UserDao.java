package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.User;
import com.expenses.walletwatch.exception.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        String sql = """
                INSERT INTO users(password, username, email)
                VALUES (?, ?, ?);
                """;
        try {
            jdbcTemplate.update(sql, user.getPassword(), user.getUsername(), user.getEmail());
        } catch (DataAccessException e) {
            throw new BadRequest();
        }
    }

    public Optional<Object> findByUsername(String username) {
        String select_by_username = """
                SELECT id
                FROM users
                WHERE username = ?
                """;

        return jdbcTemplate.execute(select_by_username);

    }
}

//TODO:
// 1. Handle error types (duplicate key, not found)
// 2. Create master class for handling all sql errors
// 3. Pass errors to the master class
