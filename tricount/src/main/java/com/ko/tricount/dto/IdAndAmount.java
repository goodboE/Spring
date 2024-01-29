package com.ko.tricount.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdAndAmount {
    public Long id;
    public String amount;
}
