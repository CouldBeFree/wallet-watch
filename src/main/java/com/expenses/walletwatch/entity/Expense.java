package com.expenses.walletwatch.entity;

import lombok.Data;

@Data
public class Expense {
    private Long id;
    private String expenses_category_name;

    public Expense(Long id, String expenses_category_name) {
        this.id = id;
        this.expenses_category_name = expenses_category_name;
    }

    public Expense() {}
}