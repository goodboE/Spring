package com.ko.tricount.service;


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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final SettlementRepository settlementRepository;
    private final SettlementParticipantRepository settlementParticipantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Expense createExpense(Long settId, User user, String name, String amount) {

        boolean isSame = false;

        // todo 예외처리
        Settlement settlement = settlementRepository.findById(settId).get();
        Set<SettlementParticipant> participants = settlement.getParticipants();

        SettlementParticipant cur = new SettlementParticipant(user, settlement);

        User user2 = userRepository.findById(user.getId()).get();
        Set<SettlementParticipant> participants2 = user2.getParticipants();

        for (SettlementParticipant participant : participants) {
            log.info("participant.user : {}", participant.getUser().getId());
            if (Objects.equals(participant.getUser().getId(), cur.getUser().getId()) && Objects.equals(participant.getSettlement().getId(), cur.getSettlement().getId())) {
                isSame = true;
                break;
            }
        }

        if (!isSame) {
            log.info("not same, add!");
            settlement.getParticipants().add(cur);
            participants2.add(cur);
            settlementParticipantRepository.save(cur);
        }

        return expenseRepository.save(new Expense(user, settlement, name, amount, LocalDateTime.now()));
    }

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }


}
