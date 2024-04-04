package com.expenses.walletwatch.dao;

import com.expenses.walletwatch.dto.GoalRequestDto;
import com.expenses.walletwatch.entity.Goal;
import com.expenses.walletwatch.exception.BadRequest;
import com.expenses.walletwatch.exception.NotFound;
import com.expenses.walletwatch.mapper.GoalRowMapper;
import com.expenses.walletwatch.utils.DateFormatParser;
import com.expenses.walletwatch.utils.GetIdFromCreatedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class GoalDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GoalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Goal createGoal(GoalRequestDto dto, Long userId) {
        try {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into goal(user_id, target_amount, already_saved, desired_date, currency, goal_name) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setLong(1, userId);
                    statement.setDouble(2, dto.getTarget_amount());
                    statement.setDouble(3, dto.getAlready_saved());
                    statement.setDate(4, DateFormatParser.ConvertDate(dto.getDesired_date()));
                    statement.setString(5, dto.getCurrency());
                    statement.setString(6, dto.getGoal_name());
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getGoalById(userId, id.get());
            }
            return null;
        } catch (RuntimeException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    public Goal getGoalById(Long userId, Object id) {
        String sql = """
                select id, target_amount, already_saved, desired_date, currency, goal_name from goal
                where user_id = ? and id = ?;
                """;
        List<Goal> data = jdbcTemplate.query(sql, new GoalRowMapper(), userId, id);
        if (data.isEmpty()) {
            String notFoundMessage = "Goal " + id + " not found";
            throw new NotFound(notFoundMessage);
        }
        return data.get(0);
    }

    public List<Goal> getGoals(Long userId) {
        String sql = """
                select id, target_amount, already_saved, desired_date, currency, goal_name from goal
                where user_id = ?;
                """;
        return jdbcTemplate.query(sql, new GoalRowMapper(), userId);
    }

    public boolean removeGoal(Long userId, Object id) {
        String sql = """
                delete from goal
                where user_id = ? and id = ?
                """;
        try {
            getGoalById(userId, id);
            jdbcTemplate.update(sql,userId, id);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFound(e.getMessage());
        }
    }

    public Goal updateGoal(int goalId, GoalRequestDto dto, Long userId) {
        try {
            getGoalById(userId, goalId);
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("update goal set target_amount = ?, already_saved = ?, desired_date = ?, currency = ?, goal_name = ? where id = ? and user_id = ?", Statement.RETURN_GENERATED_KEYS);
                    statement.setDouble(1, dto.getTarget_amount());
                    statement.setDouble(2, dto.getAlready_saved());
                    statement.setDate(3, DateFormatParser.ConvertDate(dto.getDesired_date()));
                    statement.setString(4, dto.getCurrency());
                    statement.setString(5, dto.getGoal_name());
                    statement.setInt(6, goalId);
                    statement.setLong(7, userId);
                    return statement;
                }
            }, holder);
            Optional<Object> id = GetIdFromCreatedEntity.getId(holder);
            if (id.isPresent()) {
                return getGoalById(userId, id.get());
            }
            return null;
        } catch (RuntimeException e) {
            throw new BadRequest(e.getCause());
        }
    }
}
