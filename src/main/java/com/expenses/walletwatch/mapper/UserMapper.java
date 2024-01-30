package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.User;

public class UserMapper {
    public static User mapToUser(UserRegistrationDto dto) {
        return new User(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getId());
    }
}
