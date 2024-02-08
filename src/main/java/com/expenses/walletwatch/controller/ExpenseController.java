package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.auth.JwtUtil;
import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.service.ExpenseService;
import com.expenses.walletwatch.utils.GetUserData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ExpenseController {
    private final ExpenseService expenseService;
    private JwtUtil jwtUtil;
    private final GetUserData getUserData;
    public ExpenseController(ExpenseService expenseService, JwtUtil jwtUtil, GetUserData getUserData) {

        this.getUserData = getUserData;
        this.expenseService = expenseService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("expenses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getExpenses() {
        Long user_id = getUserData.getUserIdFromToken();
        return new ResponseEntity<>(expenseService.getExpensesCategories(), HttpStatus.OK);
    }

    @PostMapping("expenses/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<Expense>> addExpense(@RequestBody ExpenseRequestDto dto) {
        return new ResponseEntity<>(expenseService.createUserExpense(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("expenses/remove")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> removeExpense(@RequestBody ExpenseRequestDto dto) {
        return new ResponseEntity<>(expenseService.deleteUserExpense(dto), HttpStatus.OK);
    }

    @GetMapping("expenses/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getAllUsersExpenses() {
        return new ResponseEntity<>(expenseService.getAllUsersExpenses(), HttpStatus.OK);
    }
}
