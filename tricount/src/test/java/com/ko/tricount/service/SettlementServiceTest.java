package com.ko.tricount.service;

import com.ko.tricount.dto.IdAndAmount;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SettlementServiceTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void getBalanceTest() {
        List<Expense> expenses = expenseRepository.findAll();
        Map<Long, Long> balanceMap = new HashMap<>();

        List<IdAndAmount> collect = expenses.stream()
                .filter(e -> e.getSettlement().getId().equals(1L))
                .map(e -> new IdAndAmount(e.getId(), e.getAmount()))
                .collect(Collectors.toList());

        int sum = 0;
        for (IdAndAmount idAndAmount : collect) {
            balanceMap.put(idAndAmount.id, Long.parseLong(idAndAmount.amount));
            sum += Integer.parseInt(idAndAmount.amount);
        }
        // balanceMap -> [user : 내야할 돈]
        int bSize = balanceMap.size();
        int avg = sum / bSize;

        for (Map.Entry<Long, Long> entry: balanceMap.entrySet()) {
            balanceMap.put(entry.getKey(), avg - entry.getValue());
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // O(N^2) 로 돌면서 줘야 하는 입장이면 줄 수 있는 만큼 다 줌
        for (Map.Entry<Long, Long> giveUser: balanceMap.entrySet()) {
            for (Map.Entry<Long, Long> receiveUser: balanceMap.entrySet()) {

                // 같은 유저 or giveUser 가 아닐 경우 or 가진 돈이 0일 경우 pass
                if (giveUser.getKey().equals(receiveUser.getKey())) continue;
                if (giveUser.getValue() <= 0 || receiveUser.getValue() >= 0) continue;

                // 주는 사람의 돈이 많은 경우 or 받아야 할 돈이 더 많은 경우
                // 5000, -2000
                // 2000, -5000
                if (giveUser.getValue() >= Math.abs(receiveUser.getValue())) {
                    balanceMap.put(giveUser.getKey(), giveUser.getValue() - Math.abs(receiveUser.getValue()));
                    balanceMap.put(receiveUser.getKey(), 0L);
                } else {
                    balanceMap.put(receiveUser.getKey(), receiveUser.getValue() + giveUser.getValue());
                    balanceMap.put(giveUser.getKey(), 0L);
                }

            }
        }

        for (Map.Entry<Long, Long> entry: balanceMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }


    }

}