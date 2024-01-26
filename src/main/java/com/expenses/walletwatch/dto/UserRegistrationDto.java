package com.expenses.walletwatch.dto;

public class UserRegistrationDto {
    public UserRegistrationDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    private String username;
    private String password;
    private String email;

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
