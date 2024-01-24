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

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SettlementService {

    private final SettlementRepository settlementRepository;

    @Transactional
    public Settlement createSettlement(String settlementName) {
        return settlementRepository.save(new Settlement(settlementName));
    }

    @Transactional
    public void deleteSettlement(Long settId) {
        settlementRepository.deleteById(settId);
    }

    public List<Settlement> findAll() {
        return settlementRepository.findAll();
    }
}
