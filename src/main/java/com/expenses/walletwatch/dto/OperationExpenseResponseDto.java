package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class OperationExpenseResponseDto {
    private long id;
    private String date;
    private String expenses_category_name;
    private int amount;

    public OperationExpenseResponseDto(long id, String date, String expenses_category_name, int amount) {
        this.id = id;
        this.date = date;
        this.expenses_category_name = expenses_category_name;
        this.amount = amount;
    }
}
