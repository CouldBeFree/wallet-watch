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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Goal> createGoal(@RequestBody GoalRequestDto dto) {
        return new ResponseEntity<>(goalService.createGoal(dto), HttpStatus.CREATED);
    }

    @GetMapping("goal/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Goal> getGoal(@PathVariable int goalId) {
        return new ResponseEntity<>(goalService.getGoal(goalId), HttpStatus.OK);
    }

    @DeleteMapping("goal/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeGoal(@PathVariable int goalId) {
        return new ResponseEntity<>(goalService.removeGoal(goalId), HttpStatus.OK);
    }

    @PutMapping("goal/{goalId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Goal> updateGoal(@PathVariable int goalId, @RequestBody GoalRequestDto dto) {
        return new ResponseEntity<>(goalService.updateGoal(goalId, dto), HttpStatus.OK);
    }
}
