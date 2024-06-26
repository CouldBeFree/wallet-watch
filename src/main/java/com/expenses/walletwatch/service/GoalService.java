package com.expenses.walletwatch.service;

import com.expenses.walletwatch.dao.GoalDao;
import com.expenses.walletwatch.dto.GoalRequestDto;
import com.expenses.walletwatch.entity.Goal;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Goal getGoal(int goalId) {
        Long userId = getUserData.getUserIdFromToken();
        return goalDao.getGoalById(userId, goalId);
    }

    public String removeGoal(int goalId) {
        Long userId = getUserData.getUserIdFromToken();
        return goalDao.removeGoal(userId, goalId) ? "Removed" : null;
    }

    public Goal updateGoal(int goalId, GoalRequestDto dto) {
        Long userId = getUserData.getUserIdFromToken();
        return goalDao.updateGoal(goalId, dto, userId);
    }

    public List<Goal> getGoals() {
        Long userId = getUserData.getUserIdFromToken();
        return goalDao.getGoals(userId);
    }
}
