package com.expenses.walletwatch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistory {
    private Long id;
    private String date;
    private Long amount;
    private Boolean expenses;
}
