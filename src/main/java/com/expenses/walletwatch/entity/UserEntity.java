package com.expenses.walletwatch.entity;

import lombok.Data;

import com.expenses.walletwatch.utils.UserValidator;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends UserValidator {
    public UserEntity(Long id, String username, String password, String email) {
        super(username, password, email);
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public UserEntity() {}

    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate date;
}
