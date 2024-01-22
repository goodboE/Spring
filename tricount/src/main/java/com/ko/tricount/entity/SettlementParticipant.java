package com.ko.tricount.entity;


import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettlementParticipant that = (SettlementParticipant) o;
        return user.equals(that.user) && settlement.equals(that.settlement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, settlement);
    }

    public SettlementParticipant(User user, Settlement settlement) {
        this.user = user;
        this.settlement = settlement;
    }
}
