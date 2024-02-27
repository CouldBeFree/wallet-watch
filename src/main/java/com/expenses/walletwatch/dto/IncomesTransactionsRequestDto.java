package com.expenses.walletwatch.dto;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class IncomesTransactionsRequestDto {

    public Date startDate;

    public Date endDate;

    public ArrayList<Integer> userIncomeCategoryId;

}
