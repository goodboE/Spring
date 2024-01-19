package com.ko.tricount.controller;


import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
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

    private final EntityManager em;

    /** 정산 생성 */
    @PostMapping("/settlement/create")
    public ResponseEntity<Settlement> createSettlement(@RequestParam(name = "settlementName") String settlementName) {
        log.info("[createSettlement] user={}", UserContext.getCurrentUser().getLoginId());

        // user 가 영속상태인지 확인
        User currentUser = UserContext.getCurrentUser();
        if (em.contains(currentUser)) {
            log.info("currentUser 는 영속상태입니다.");
        } else {
            log.info("currentUser 는 영속상태가 아닙니다.");
        }



        return new ResponseEntity(settlementService.createAndJoinSettlement(settlementName, UserContext.getCurrentUser()), HttpStatus.OK);
    }

}
