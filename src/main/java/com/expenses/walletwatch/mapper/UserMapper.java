package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.UserEntity;

public class UserMapper {
    public static UserEntity mapToUser(UserEntity userEntity) {
        return new UserEntity(userEntity.getUsername(), userEntity.getPassword(), userEntity.getEmail(), userEntity.getId());
    }
}
