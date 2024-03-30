package com.ko.shop.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemForm {

    @NotEmpty(message = "이름이 비어있습니다.")
    private String name;

    @NotEmpty
    private String content;

    @NotNull
    private int price;

    @NotNull
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
