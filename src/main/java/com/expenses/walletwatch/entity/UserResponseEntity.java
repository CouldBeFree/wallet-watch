package com.expenses.walletwatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseEntity {
    private Long id;
    private String username;
    private String email;

}
