package com.ko.tricount.service;


import com.ko.tricount.dto.BalanceDto;
import com.ko.tricount.dto.IdAndAmount;
import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.ExpenseRepository;
import com.ko.tricount.repository.SettlementParticipantRepository;
import com.ko.tricount.repository.SettlementRepository;
import com.ko.tricount.repository.UserRepository;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SettlementService {

    private final SettlementRepository settlementRepository;
    private final ExpenseRepository expenseRepository;


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

        List<BalanceDto> result = new ArrayList<>();
        List<Expense> expenses = expenseRepository.findAll();
        Map<Long, Integer> balanceMap = new HashMap<>();

        List<IdAndAmount> collect = expenses.stream()
                .filter(e -> e.getSettlement().getId().equals(settId))
                .map(e -> new IdAndAmount(e.getId(), e.getAmount()))
                .collect(Collectors.toList());

        int sum = 0;
        for (IdAndAmount idAndAmount : collect) {
            balanceMap.put(idAndAmount.id, Integer.parseInt(idAndAmount.amount));
            sum += Integer.parseInt(idAndAmount.amount);
        }

        for (IdAndAmount idAndAmount : collect) {
            log.info("idAndAmount: {}", idAndAmount);
        }

        // balanceMap -> [user : 내야할 돈]
        int bSize = balanceMap.size();
        int avg = sum / bSize;
        balanceMap.replaceAll((k, v) -> avg - v);

        for (Map.Entry<Long, Integer> giveUser: balanceMap.entrySet()) {
            for (Map.Entry<Long, Integer> receiveUser: balanceMap.entrySet()) {
                if (giveUser.getKey().equals(receiveUser.getKey())) continue;
                if (giveUser.getValue() <= 0 || receiveUser.getValue() >= 0) continue;

                if (giveUser.getValue() >= Math.abs(receiveUser.getValue())) {
                    balanceMap.put(giveUser.getKey(), giveUser.getValue() - Math.abs(receiveUser.getValue()));
                    result.add(new BalanceDto(giveUser.getKey(), Math.abs(receiveUser.getValue()), receiveUser.getKey()));
                    balanceMap.put(receiveUser.getKey(), 0);

                } else {
                    balanceMap.put(receiveUser.getKey(), receiveUser.getValue() + giveUser.getValue());
                    result.add(new BalanceDto(giveUser.getKey(), giveUser.getValue(), receiveUser.getKey()));
                    balanceMap.put(giveUser.getKey(), 0);

                }

            }
        }


        return result;

    }

    public List<Settlement> findAll() {
        return settlementRepository.findAll();
    }


}
