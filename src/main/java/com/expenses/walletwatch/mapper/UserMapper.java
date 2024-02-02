package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.UserEntity;

public class UserMapper {
    public static UserEntity mapToUser(UserRegistrationDto dto) {
        return new UserEntity(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getEmail());
    }
}
