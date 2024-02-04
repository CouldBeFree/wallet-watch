package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IncomeRequestDto {
    private int income_id;

    public IncomeRequestDto(int income_id) {
        this.income_id = income_id;
    }
}
