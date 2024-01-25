package com.ko.tricount.service;


import com.ko.tricount.dto.BalanceDto;
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

    public List<BalanceDto> startSettlement(Long settId) {

        /*
         * 1. 현재 정산의 지출 내역을 모두 가져온다.
         * 2. 유저마다 ('+' or '-' 여부, 차액을 HashMap 에 저장한다.)
         * 3. 유저들을 탐색하며 줘야할 사람이 받는사람에게 금액을 전달하고, BalanceDto 를 결과 List 에 추가한다.
         */

        // 현재 정산의 모든 지출 내역


    }

    public List<Settlement> findAll() {
        return settlementRepository.findAll();
    }
}
