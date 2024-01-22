package com.ko.tricount.controller;


import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.service.ExpenseService;
import com.ko.tricount.service.SettlementParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;
    private final SettlementParticipantService settlementParticipantService;

    @PostMapping("/expense/create")
    public ResponseEntity<Expense> createExpense(
            @RequestParam(name = "settId") Long settId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "amount") String amount) {

        expenseService.createExpense(settId, name, amount);

        return ResponseEntity.ok().build();
    }

}
