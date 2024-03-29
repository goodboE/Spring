package com.ko.shop.dto;

import lombok.Data;

@Data
public class OrderForm {

    private String address;
    private int count;

    public OrderForm() {}

    public OrderForm(String address, int count) {
        this.address = address;
        this.count = count;
    }
}
