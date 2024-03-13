package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OperationIncomeRequestDto {
    private int amount;
    private String date;
    private int income_category_id;
    private String income_category_name;

    public OperationIncomeRequestDto(int amount, String date, int income_category_id, String income_category_name) {
        this.amount = amount;
        this.date = date;
        this.income_category_id = income_category_id;
        this.income_category_name = income_category_name;
    }
}
