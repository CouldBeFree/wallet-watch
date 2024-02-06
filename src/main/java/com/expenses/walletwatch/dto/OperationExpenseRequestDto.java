package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OperationExpenseRequestDto {
    private int amount;
    private String date;
    private int expense_category_id;

    public OperationExpenseRequestDto(int amount, String date, int expense_category_id) {
        this.amount = amount;
        this.date = date;
        this.expense_category_id = expense_category_id;
    }
}
