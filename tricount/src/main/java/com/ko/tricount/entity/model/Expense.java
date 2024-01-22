package com.ko.tricount.entity.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id @GeneratedValue
    @Column(name = "expense_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sett_id")
    private Settlement settlement;

    private String name;
    private String amount;
    private LocalDateTime date;

    public Expense(User user, Settlement settlement, String name, String amount, LocalDateTime date) {
        this.user = user;
        this.settlement = settlement;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }
}
