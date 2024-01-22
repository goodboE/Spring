package com.ko.tricount.controller;


import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.SettlementParticipantService;
import com.ko.tricount.service.SettlementService;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SettlementController {

    private final SettlementService settlementService;
    private final SettlementParticipantService settlementParticipantService;


    /** 정산 생성 */
    @PostMapping("/settlement/create")
    public ResponseEntity<Settlement> createAndJoinSettlement(@RequestParam(name = "settlementName") String settlementName) {
        log.info("[createSettlement] user={}", UserContext.getCurrentUser().getLoginId());

        Settlement settlement = settlementService.createSettlement(settlementName);
        settlementParticipantService.createSettParti(settlement, UserContext.getCurrentUser());

        return ResponseEntity.ok().build();
    }

}
