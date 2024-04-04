package com.expenses.walletwatch.entity;

import lombok.Data;

@Data
public class Goal {
    private Long id;
    private int target_amount;
    private String currency;
    private int already_saved;
    private String desired_date;
    private String goal_name;
}
