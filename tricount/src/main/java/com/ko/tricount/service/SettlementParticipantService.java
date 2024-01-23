package com.ko.tricount.service;


import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.SettlementParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SettlementParticipantService {

    private final SettlementParticipantRepository settlementParticipantRepository;

    @Transactional
    public SettlementParticipant createSettParti(Settlement settlement, User user) {
        SettlementParticipant sp = settlementParticipantRepository.save(new SettlementParticipant(user, settlement));
        settlement.getParticipants().add(sp);
        user.getParticipants().add(sp);
        return sp;
    }

}
