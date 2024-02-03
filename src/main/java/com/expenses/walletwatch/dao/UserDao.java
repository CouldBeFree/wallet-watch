package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.User;
import com.expenses.walletwatch.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(User user) throws RuntimeException {
        String sql = """
                INSERT INTO users(password, username, email)
                VALUES (?, ?, ?);
                """;
        return jdbcTemplate.update(sql, user.getPassword(), user.getUsername(), user.getEmail());
    }


    public User getUserByEmailAndUsername(User user) throws RuntimeException {
        String sql = """
                select * from users
                where email = ? or username = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), user.getEmail(), user.getUsername());
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }
    public User getUserByUsername(String username) throws RuntimeException {
        String sql = """
                SELECT id, username, email
                FROM users
                WHERE username = ?
                """;
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
    }

    public User getUserByEmail(String email) throws RuntimeException {
        String sql = """
                SELECT id, username, email, password
                FROM users
                WHERE email = ?
                """;
        return jdbcTemplate.queryForObject(sql, new UserRowMapper(), email);
    }
}
