package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.UserDao;
import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.dto.UserResponseDto;
import com.expenses.walletwatch.entity.UserEntity;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.mapper.UserMapper;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;
    private final GetUserData getUserData;

    public UserService(UserDao userDao, GetUserData getUserData) {
        this.userDao = userDao;
        this.getUserData = getUserData;
    }

    public UserResponseDto getLoggedInUser() {
        Long userId = getUserData.getUserIdFromToken();
        try {
            UserEntity user = userDao.getUserById(userId);
            return new UserResponseDto(
                    user.getEmail(),
                    user.getUsername(),
                    user.getId()
            );
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    public UserRegistrationDto registerUser(UserEntity userEntity) {
        UserEntity user = UserMapper.mapToUser(userEntity);
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
