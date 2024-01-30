package com.expenses.walletwatch.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private String username;
    private String password;
    private String email;

    public UserValidator(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserValidator() {};

    boolean validateEmail() {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(this.email);
        return matcher.matches();
    }

    boolean validateUserName() {
        return this.username.length() < 100;
    }

    boolean validatePassword() {
        return this.password.length() < 100;
    }

    public void validateUser() throws RuntimeException {
        if (!validateEmail()) {
            throw new RuntimeException("Email is not valid");
        }
        if (!validateUserName()) {
            throw new RuntimeException("Username length should be less then 100 characters");
        }
        if (!validatePassword()) {
            throw new RuntimeException("Password length should be less then 100 characters");
        }
    }
}
