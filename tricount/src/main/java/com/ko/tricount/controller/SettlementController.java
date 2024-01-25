package com.ko.tricount.controller;


import com.ko.tricount.dto.BalanceDto;
import com.ko.tricount.dto.Result;
import com.ko.tricount.dto.SettlementDto;
import com.ko.tricount.entity.SettlementParticipant;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.service.SettlementParticipantService;
import com.ko.tricount.service.SettlementService;
import com.ko.tricount.service.UserService;
import com.ko.tricount.util.UserContext;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SettlementController {

    private final SettlementService settlementService;
    private final UserService userService;
    private final SettlementParticipantService settlementParticipantService;
    private final EntityManager em;

    /** 전체 조회 */
    @GetMapping("/settlements")
    public Result findAll() {
        List<Settlement> settlements = settlementService.findAll();

        List<SettlementDto> settlementDtos = settlements.stream()
                .map(s -> {
                    List<Long> userIds = s.getUsers().stream()
                            .map(User::getId)
                            .collect(Collectors.toList());
                    return new SettlementDto(s.getId(), s.getName(), userIds);
                })
                .collect(Collectors.toList());

        return new Result(settlementDtos.size(), settlementDtos);
    }

    /** 정산 생성 */
    @PostMapping("/settlement/create")
    public ResponseEntity<Settlement> createAndJoinSettlement(@RequestParam(name = "settlementName") String settlementName) {
        log.info("[createSettlement] user={}", UserContext.getCurrentUser().getLoginId());

        Settlement settlement = settlementService.createSettlement(settlementName);
        Long findUserId = UserContext.getCurrentUser().getId();
        User user = userService.findById(findUserId);
        settlementParticipantService.createSettParti(settlement, user);

        return ResponseEntity.ok().build();
    }

    /** 정산 삭제 */
    @PostMapping("/settlement/delete/{id}")
    public ResponseEntity<Settlement> deleteSettlement(@PathVariable(name = "id") Long id) {
        settlementService.deleteSettlement(id);
        return ResponseEntity.ok().build();
    }

    /** 정산 로직 실행 후 삭제 */
    @PostMapping("/settlement/start/{id}")
    public Result startSettlementAndDelete(@PathVariable(name = "id") Long id) {
        List<BalanceDto> balanceDtos = settlementService.startSettlement(id);
        return new Result(balanceDtos.size(), balanceDtos);
    }

}
