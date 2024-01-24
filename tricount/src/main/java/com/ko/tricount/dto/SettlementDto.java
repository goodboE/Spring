package com.ko.tricount.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SettlementDto {

    private Long id;
    private String name;
    private List<Long> users;

}
