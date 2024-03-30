package com.ko.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OrderForm {

    @NotEmpty
    private String address;

    @NotEmpty
    private int count;

    public OrderForm() {}

    public OrderForm(String address, int count) {
        this.address = address;
        this.count = count;
    }
}
