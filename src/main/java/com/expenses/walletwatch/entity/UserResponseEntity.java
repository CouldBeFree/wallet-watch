package com.expenses.walletwatch.entity;

import com.expenses.walletwatch.utils.UserValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseEntity {
    private Long id;
    private String username;
    private String email;

}
