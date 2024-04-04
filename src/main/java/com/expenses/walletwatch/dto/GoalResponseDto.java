package com.expenses.walletwatch.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GoalResponseDto {
    private int target_amount;
    private int already_saved;
    private String desired_date;
    private String currency;
    private String goal_name;
}
