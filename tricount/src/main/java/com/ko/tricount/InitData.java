package com.ko.tricount;


import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.ExpenseRepository;
import com.ko.tricount.repository.SettlementParticipantRepository;
import com.ko.tricount.repository.SettlementRepository;
import com.ko.tricount.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;
    private final SettlementParticipantRepository settlementParticipantRepository;
    private final ExpenseRepository expenseRepository;

    @PostConstruct
    public void init() {
        User user1 = userRepository.save(new User("id1", "pw1", "name-1"));
        User user2 = userRepository.save(new User("id2", "pw2", "name-3"));
        User user3 = userRepository.save(new User("id3", "pw3", "name-3"));

        Settlement mt = settlementRepository.save(new Settlement("MT"));
        Settlement party = settlementRepository.save(new Settlement("파티"));

        expenseRepository.save(new Expense(user1, mt, "고기", "10000", LocalDateTime.now()));
        expenseRepository.save(new Expense(user2, mt, "술", "20000", LocalDateTime.now()));
        expenseRepository.save(new Expense(user3, party, "안주", "15000", LocalDateTime.now()));

        settlementParticipantRepository.save(new SettlementParticipant(user1, mt));
        settlementParticipantRepository.save(new SettlementParticipant(user2, mt));
        settlementParticipantRepository.save(new SettlementParticipant(user3, party));
    }

}
