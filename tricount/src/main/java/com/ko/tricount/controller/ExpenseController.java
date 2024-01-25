package com.ko.tricount.controller;


import com.ko.tricount.dto.ExpenseDto;
import com.ko.tricount.dto.Result;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.entity.model.Settlement;
import com.ko.tricount.entity.model.User;
import com.ko.tricount.repository.SettlementRepository;
import com.ko.tricount.repository.UserRepository;
import com.ko.tricount.service.ExpenseService;
import com.ko.tricount.service.SettlementParticipantService;
import com.ko.tricount.service.UserService;
import com.ko.tricount.util.SessionConst;
import com.ko.tricount.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;
    private final SettlementRepository settlementRepository;
    private final UserService userService;
    private final SettlementParticipantService settlementParticipantService;

    @PostMapping("/expense/create")
    public ResponseEntity<Expense> createExpense(
            @RequestParam(name = "settId") Long settId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") String amount) {

        expenseService.createExpense(settId, name, amount);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/expense/{settId}")
    public Result getExpenses(@PathVariable(name = "settId") Long id, HttpServletRequest request) throws Exception{
        List<Expense> expenses = expenseService.findAll();

        Settlement settlement = settlementRepository.findById(id).get();
        // log.info("user -> {}", UserContext.getCurrentUser());

        // 현재 유저
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
        log.info("currentUser = {}", currentUser);
        log.info("currentUser.id = {}", currentUser.getId());

        // 정산에 참여한 유저들의 id 집합
        Set<Long> userIds = settlement.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        if (!userIds.contains(currentUser.getId())) {
            log.info("현재 유저의 id 미포함! {}", currentUser.getId());
            throw new RuntimeException("정산에 참여한 유저만 지출 내역을 확인할 수 있습니다.");
        }


        List<ExpenseDto> expenseDtos = expenses.stream()
                .filter(e -> e.getSettlement().getId().equals(id))
                .map(e -> new ExpenseDto(e.getId(), e.getSettlement().getId(), e.getUser().getId(), e.getAmount(), e.getName()))
                .collect(Collectors.toList());

        return new Result(expenseDtos.size(), expenseDtos);
    }

}
