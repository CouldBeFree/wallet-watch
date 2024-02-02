package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class ExpenseDto {
    private Long id;
    private String expenses_category_name;

    public ExpenseDto(Long id, String expenses_category_name) {
        this.id = id;
        this.expenses_category_name = expenses_category_name;
    }
}
