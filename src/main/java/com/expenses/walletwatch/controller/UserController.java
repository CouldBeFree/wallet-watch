package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto dto) {
        userService.registerUser(dto);
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    };
}
