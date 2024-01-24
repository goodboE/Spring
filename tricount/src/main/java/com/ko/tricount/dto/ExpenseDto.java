package com.ko.tricount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private Long settlement_id;
    private Long user_id;
    private String amount;
    private String name;
}
