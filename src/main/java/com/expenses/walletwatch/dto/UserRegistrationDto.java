package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    public UserRegistrationDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    private String username;
    private String password;
    private String email;
}
