package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.GoalDao;
import com.expenses.walletwatch.dto.GoalRequestDto;
import com.expenses.walletwatch.entity.Goal;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

@Service
public class GoalService {
    private final GoalDao goalDao;
    private final GetUserData getUserData;

    public GoalService(GoalDao goalDao, GetUserData getUserData) {
        this.goalDao = goalDao;
        this.getUserData = getUserData;
    }

    public Goal createGoal(GoalRequestDto dto) {
        Long userId = getUserData.getUserIdFromToken();
        return goalDao.createGoal(dto, userId);
    }
}
