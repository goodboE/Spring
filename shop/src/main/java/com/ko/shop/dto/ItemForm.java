package com.ko.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ItemForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String content;

    @NotEmpty
    private int price;

    @NotEmpty
    private int quantity;

    public ItemForm() {

    }

    public ItemForm(String name, String content, int price, int quantity) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
    }

}
