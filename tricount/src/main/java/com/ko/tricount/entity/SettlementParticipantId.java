package com.ko.tricount.entity;

import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;

import java.io.Serializable;
import java.util.Objects;

public class SettlementParticipantId implements Serializable {

    private User user;
    private Settlement settlement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SettlementParticipantId that = (SettlementParticipantId) o;
        return Objects.equals(user, that.user) && Objects.equals(settlement, that.settlement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, settlement);
    }
}
