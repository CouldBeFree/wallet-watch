package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.UserDao;
import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.User;
import com.expenses.walletwatch.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(UserRegistrationDto userRegistrationDto) {
        User user = UserMapper.mapToUser(userRegistrationDto);
        userDao.save(user);
    };
}
