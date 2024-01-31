package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExpenseRequestDto {
    private int expense_id;

    public ExpenseRequestDto(int expense_id) {
        this.expense_id = expense_id;
    }
}
