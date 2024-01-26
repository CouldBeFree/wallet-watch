package com.expenses.walletwatch.entity;

import java.time.LocalDate;

public class User {
    public User(String username, String password, String email, Integer id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    private Integer id;
    private String username;
    private String password;
    private String email;
    private LocalDate date;

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
