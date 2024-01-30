package com.expenses.walletwatch.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class UserRegistrationDto {
//    public UserRegistrationDto(String username, String password, String email) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//    }

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
