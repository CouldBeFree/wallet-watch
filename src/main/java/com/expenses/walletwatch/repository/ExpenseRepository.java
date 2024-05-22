package com.expenses.walletwatch.repository;

import com.expenses.walletwatch.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
