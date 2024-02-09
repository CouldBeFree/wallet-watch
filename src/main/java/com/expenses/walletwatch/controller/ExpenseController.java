package com.expenses.walletwatch.controller;

import com.expenses.walletwatch.auth.JwtUtil;
import com.expenses.walletwatch.dto.ExpenseRequestDto;
import com.expenses.walletwatch.dto.ExpensesTransactionsRequestDto;
import com.expenses.walletwatch.entity.Expense;
import com.expenses.walletwatch.entity.UserExpenseStatistic;
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
    private final ExpensesTransactionsRequestDto expensesTransactionsRequestDto;
    private JwtUtil jwtUtil;
    private final GetUserData getUserData;
    public ExpenseController(
            ExpenseService expenseService,
            JwtUtil jwtUtil,
            GetUserData getUserData,
            ExpensesTransactionsRequestDto expensesTransactionsRequestDto) {

        this.getUserData = getUserData;
    public ExpenseController(ExpenseService expenseService, JwtUtil jwtUtil) {
        this.expenseService = expenseService;
        this.jwtUtil = jwtUtil;
        this.expensesTransactionsRequestDto = expensesTransactionsRequestDto;
    }

    @GetMapping("expenses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getExpenses() {
        Long user_id = getUserData.getUserIdFromToken();
        return new ResponseEntity<>(expenseService.getExpensesCategories(), HttpStatus.OK);
    }

    @PostMapping("expenses")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Expense> addExpense(@RequestBody ExpenseRequestDto dto) {
        return new ResponseEntity<>(expenseService.createUserExpense(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("expenses/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeExpense(@PathVariable int categoryId) {
        return new ResponseEntity<>(expenseService.deleteUserExpense(categoryId), HttpStatus.OK);
    }

    @GetMapping("expenses/mine")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Expense>> getAllUsersExpenses() {
        return new ResponseEntity<>(expenseService.getAllUsersExpenses(), HttpStatus.OK);
    }

    @GetMapping("expenses/user/statistic")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserExpenseStatistic>> getUserTransactionStatistic(
            @RequestBody ExpensesTransactionsRequestDto userExpensesTransactionsDto
    ){
        return new ResponseEntity<>(expenseService.userTransactionsStatisticForPeriod(
                userExpensesTransactionsDto.startDate,
                userExpensesTransactionsDto.endDate
        ), HttpStatus.OK);
    }

    @GetMapping("expenses/user/statistic/by-category")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserExpenseStatistic>> getUserTransactionStatisticByCategory(
            @RequestBody ExpensesTransactionsRequestDto userExpensesTransactionsDto
    ){
        return new ResponseEntity<>(expenseService.userTransactionsStatisticByCategory(
                userExpensesTransactionsDto.userCategoryId,
                userExpensesTransactionsDto.startDate,
                userExpensesTransactionsDto.endDate
        ), HttpStatus.OK);
    }

}
