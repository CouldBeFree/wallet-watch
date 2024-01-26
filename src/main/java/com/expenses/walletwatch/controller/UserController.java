package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegistrationDto dto) {
        userService.registerUser(dto);
    };
}
