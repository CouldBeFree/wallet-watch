package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class TotalDto {
    private Long total_expense;
    private Long total_income;

    public TotalDto(Long total_expense, Long total_income) {
        this.total_expense = total_expense;
        this.total_income = total_income;
    }
}
