package com.expenses.walletwatch.dto;

import lombok.Data;

@Data
public class TransactionHistoryDto {
    private Long id;
    private Long amount;
    private Boolean expenses;
    private String transaction_date;
    private String category_name;

    public TransactionHistoryDto(Long id, Long amount, Boolean expenses, String transaction_date, String category_name) {
        this.id = id;
        this.amount = amount;
        this.expenses = expenses;
        this.transaction_date = transaction_date;
        this.category_name = category_name;
    }
}
