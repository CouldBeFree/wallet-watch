package com.expenses.walletwatch.entity;

import lombok.Data;

@Data
public class Income {
    private Long id;
    private String incomes_category_name;

    public Income(Long id, String incomes_category_name) {
        this.id = id;
        this.incomes_category_name = incomes_category_name;
    }

    public Income(){}
}
