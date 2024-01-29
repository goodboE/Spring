package com.ko.tricount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceDto {
    private Long senderUserNo;
    private int sendAmount;
    private Long receiverUserNo;
}
