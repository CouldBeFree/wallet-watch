package com.expenses.walletwatch.entity;

import lombok.Data;

import com.expenses.walletwatch.utils.UserValidator;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Data
public class User extends UserValidator {
    public User(String username, String password, String email, Long id) {
        super(username, password, email);
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public User() {}

    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDate date;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                '}';
    }
}
