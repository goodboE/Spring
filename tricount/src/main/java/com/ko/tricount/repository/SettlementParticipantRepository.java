package com.ko.tricount.repository;

import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.SettlementParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementParticipantRepository extends JpaRepository<SettlementParticipant, Long> {
}
