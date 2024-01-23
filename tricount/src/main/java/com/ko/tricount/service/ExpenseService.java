package com.ko.tricount.service;


import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.ExpenseRepository;
import com.ko.tricount.repository.SettlementParticipantRepository;
import com.ko.tricount.repository.SettlementRepository;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
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
    private final EntityManager em;

    @Transactional
    public Expense createExpense(Long settId, String name, String amount) {

        boolean isSame = false;

        // todo 예외처리
        Settlement settlement = settlementRepository.findById(settId).get();

        Set<SettlementParticipant> participants = settlement.getParticipants();
        SettlementParticipant cur = new SettlementParticipant(UserContext.getCurrentUser(), settlement);
        Set<SettlementParticipant> participants2 = UserContext.getCurrentUser().getParticipants();
        for (SettlementParticipant participant : participants) {
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

        return expenseRepository.save(new Expense(UserContext.getCurrentUser(), settlement, name, amount, LocalDateTime.now()));
    }


}
