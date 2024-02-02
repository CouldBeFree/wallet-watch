package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.UserDao;
import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.UserEntity;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserRegistrationDto registerUser(UserRegistrationDto userRegistrationDto) {
        UserEntity user = UserMapper.mapToUser(userRegistrationDto);
        try {
            user.validateUser();
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
        Object isUserExist = userDao.getUserByEmailAndUsername(user);
        if (isUserExist != null) {
            throw new BadRequest("Email or Username already exists");
        }
        userDao.save(user);

        UserEntity getUser = userDao.getUserByEmailAndUsername(user);
        return new UserRegistrationDto(getUser.getId(), user.getEmail(), user.getUsername());
    }
}
