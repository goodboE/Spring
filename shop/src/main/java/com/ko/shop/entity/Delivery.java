package com.ko.shop.entity;

import com.ko.shop.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String address;

    public Delivery(DeliveryStatus status, String address) {
        this.status = status;
        this.address = address;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
