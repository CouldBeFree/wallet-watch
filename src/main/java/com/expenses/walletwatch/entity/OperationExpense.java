package com.expenses.walletwatch.entity;

import lombok.Data;

@Data
public class OperationExpense {
    private Long id;
    private int amount;
    private String date;
    private int user_id;
    private String expense_category_id;
    private String expenses_category_name;

    public OperationExpense() {};

    public OperationExpense(Long id, int amount, String date, String expense_category_id, int user_id, String expenses_category_name) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.expense_category_id = expense_category_id;
        this.user_id = user_id;
        this.expenses_category_name = expenses_category_name;
    }
}
