package com.ko.tricount.entity;


import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
public class SettlementParticipant {

    @Id @GeneratedValue
    @Column(name = "sett_parti_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sett_id")
    private Settlement settlement;
}
