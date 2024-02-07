package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OperationExpenseRequestDto {
    private int amount;
    private String date;
    private int expense_category_id;
    private String expenses_category_name;

    public OperationExpenseRequestDto(int amount, String date, int expense_category_id, String expenses_category_name) {
        this.amount = amount;
        this.date = date;
        this.expense_category_id = expense_category_id;
        this.expenses_category_name = expenses_category_name;
    }
}
