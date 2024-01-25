package com.ko.tricount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BalanceDto {
    private Long senderUserNo;
    private String senderUserName;
    private int sendAmount;
    private Long receiverUserNo;
    private String receiverUserName;
}
