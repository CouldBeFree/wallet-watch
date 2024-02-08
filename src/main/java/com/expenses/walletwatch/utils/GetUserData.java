package com.expenses.walletwatch.utils;

import com.expenses.walletwatch.dao.UserDao;
import com.expenses.walletwatch.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetUserData {

    public final UserDao userDao;

    public GetUserData(UserDao userDao){
        this.userDao = userDao;
    }

    public Long getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        UserEntity user = userDao.getUserByEmail(email);
        return user.getId();

    }
}
