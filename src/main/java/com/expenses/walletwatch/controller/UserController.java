package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.auth.JwtUtil;
import com.expenses.walletwatch.dto.LoginReqDto;
import com.expenses.walletwatch.dto.UserRegistrationDto;
import com.expenses.walletwatch.entity.UserEntity;
import com.expenses.walletwatch.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.expenses.walletwatch.exception.ErrorRes;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto dto) {
        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode((dto.getPassword())));
        userService.registerUser(user);
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    };

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody LoginReqDto loginReqDto) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginReqDto.getEmail(),
                                    loginReqDto.getPassword()
                            )
                    );
            UserEntity user = new UserEntity(loginReqDto.getEmail());
            String token = jwtUtil.createToken(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (BadCredentialsException e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
