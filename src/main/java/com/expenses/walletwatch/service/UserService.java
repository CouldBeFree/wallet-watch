package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.UserDao;
import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.User;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.UserMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = UserMapper.mapToUser(userRegistrationDto);
        try {
            user.validateUser();
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
        try {
            Object queryResult = userDao.getUserByEmail(user);
            if (queryResult != null) {
                throw new BadRequest("Email already exists");
            }
        } catch (EmptyResultDataAccessException ignore) {}
        try {
            Object userQueryResult = userDao.getUserByUsername(user);
            if (userQueryResult != null) {
                throw new BadRequest("Username already exists");
            }
        } catch (EmptyResultDataAccessException ignore) {}
    };
}
