package com.ko.tricount.entity;


import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity @Getter
@NoArgsConstructor
@ToString
public class SettlementParticipant {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sett_id")
    private Settlement settlement;

    public SettlementParticipant(User user, Settlement settlement) {
        this.user = user;
        this.settlement = settlement;
    }

}
