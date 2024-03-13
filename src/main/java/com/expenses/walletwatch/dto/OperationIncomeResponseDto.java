package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class OperationIncomeResponseDto {
    private long id;
    private String date;
    private String incomes_category_name;
    private int amount;

    public OperationIncomeResponseDto(long id, String date, String incomes_category_name, int amount) {
        this.id = id;
        this.date = date;
        this.incomes_category_name = incomes_category_name;
        this.amount = amount;
    }
}
