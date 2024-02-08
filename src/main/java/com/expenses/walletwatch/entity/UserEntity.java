package com.expenses.walletwatch.entity;

import lombok.*;

import com.expenses.walletwatch.utils.UserValidator;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends UserValidator {
    public UserEntity(String username, String password, String email, Long id) {
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

    public UserEntity(String email, String password) {
        this.password = password;
        this.email = email;
    }

    public UserEntity(String email) {
        this.email = email;
    }
}
