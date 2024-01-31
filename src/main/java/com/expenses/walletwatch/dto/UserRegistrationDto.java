package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private Long id;
    private String username;
    private String password;
    private String email;

    public UserRegistrationDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
