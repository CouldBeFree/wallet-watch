package com.expenses.walletwatch.entity;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    public User(String username, String password, String email, Long id) {
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

    public Long getId() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    boolean validateEmail() {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(this.email);
        return matcher.matches();
    }

    public void validateUser() throws RuntimeException {
        if (!validateEmail()) {
            throw new RuntimeException("Email is not valid");
        }
    }

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
