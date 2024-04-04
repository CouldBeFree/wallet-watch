package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;

    public UserResponseDto(String email, String username, Long id) {
        this.email = email;
        this.username = username;
        this.id = id;
    }
}
