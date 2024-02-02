package com.expenses.walletwatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    private Long id;
    private String username;
    private String password;
    private String email;

    public UserRegistrationDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}
