package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.dto.GoalRequestDto;
import com.expenses.walletwatch.entity.Goal;
import com.expenses.walletwatch.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/")
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("goal")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Goal> createGoal(@RequestBody GoalRequestDto dto) {
        return new ResponseEntity<>(goalService.createGoal(dto), HttpStatus.OK);
    }
}
