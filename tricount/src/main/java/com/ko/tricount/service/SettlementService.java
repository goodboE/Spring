package com.ko.tricount.service;


import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.SettlementParticipantRepository;
import com.ko.tricount.repository.SettlementRepository;
import com.ko.tricount.repository.UserRepository;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final UserRepository userRepository;
    private final SettlementParticipantRepository settlementParticipantRepository;
    private final EntityManager em;

    @Transactional
    public Settlement createAndJoinSettlement(String settlementName, User user) {

        if (em.contains(user)) {
            log.info("[1] user 는 영속상태입니다.");
        } else {
            log.info("[1] user 는 영속상태가 아닙니다.");
        }

        // todo 수정!!!
        Settlement settlement = settlementRepository.save(new Settlement(settlementName));
        log.info("settlement_id = {}", settlement.getId());
        log.info("user_id = {}", user.getId());

        // User persisted_user = userRepository.findById(user.getId()).get();

        SettlementParticipant sp = settlementParticipantRepository.save(new SettlementParticipant(user, settlement));
        // settlement.getParticipants().add(sp);

        log.info("[createAndJoinSettlement] settlement = {}", settlement.getName());
        log.info("[createAndJoinSettlement] user = {}", user.getLoginId());
        return settlement;
    }
}
