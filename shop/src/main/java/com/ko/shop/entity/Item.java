package com.ko.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private String content;
    private int price;
    private int quantity;

    public void addQuantity(int num) {
        this.quantity += num;
    }

    public void removeQuantity(int num) {
        this.quantity -= num;
    }


    public Item(String name, String content, int price, int quantity) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateAttributes(String name, String content, int price, int quantity) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
    }
}
