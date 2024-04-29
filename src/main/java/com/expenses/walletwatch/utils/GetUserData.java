package com.expenses.walletwatch.utils;

import com.expenses.walletwatch.model.Person;
import com.expenses.walletwatch.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetUserData {

    private final UserRepository userRepository;

    public GetUserData(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Person user = userRepository.findUserByEmail(email);
        return user.getId();
    }
}
