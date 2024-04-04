package com.expenses.walletwatch.mapper;

import com.expenses.walletwatch.entity.Goal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalRowMapper implements RowMapper<Goal> {
    @Override
    public Goal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Goal goal = new Goal();
        goal.setId(rs.getLong("id"));
        goal.setGoal_name(rs.getString("goal_name"));
        goal.setCurrency(rs.getString("currency"));
        goal.setDesired_date(rs.getString("desired_date"));
        goal.setAlready_saved(rs.getInt("already_saved"));
        goal.setTarget_amount(rs.getInt("target_amount"));

        return goal;
    }
}
