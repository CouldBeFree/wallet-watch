package com.expenses.walletwatch.entity;

import lombok.Data;

@Data
public class OperationIncome {
    private Long id;
    private int amount;
    private String date;
    private int user_id;
    private String income_category_id;
    private String incomes_category_name;

    public OperationIncome() {};

    public OperationIncome(Long id, int amount, String date, String income_category_id, int user_id, String incomes_category_name) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.income_category_id = income_category_id;
        this.user_id = user_id;
        this.incomes_category_name = incomes_category_name;
    }
}
