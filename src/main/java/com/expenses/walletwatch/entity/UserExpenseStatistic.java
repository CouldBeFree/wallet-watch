package com.expenses.walletwatch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExpenseStatistic {
    private String expenses_category_name;
    private Float amount_sum;
    private Integer id;
    private String color;
}
