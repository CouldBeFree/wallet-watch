package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.entity.User;
import com.expenses.walletwatch.exception.ApiRequestException;
import com.expenses.walletwatch.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.file.AccessDeniedException;

@Repository
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    public int save(User user) throws RuntimeException {
//        String sql = """
//                INSERT INTO users(password, username, email)
//                VALUES (?, ?, ?);
//                """;
//        return jdbcTemplate.update(sql, user.getPassword(), user.getUsername(), user.getEmail());
//    }


    public Object getUserByEmailAndUsername(User user) throws RuntimeException {
        String sql = """
                select * from users
                where email = ? or username = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{user.getEmail(), user.getUsername()}, new BeanPropertyRowMapper(UserRowMapper.class));
        }
        catch (EmptyResultDataAccessException ignore) {return null;}
    }
}
