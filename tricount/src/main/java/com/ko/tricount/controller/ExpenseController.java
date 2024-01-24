package com.ko.tricount.controller;


import com.ko.tricount.dto.ExpenseDto;
import com.ko.tricount.dto.Result;
import com.ko.tricount.entity.model.Expense;
import com.ko.tricount.service.ExpenseService;
import com.ko.tricount.service.SettlementParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/expense/{settId}")
    public Result getExpenses(@PathVariable(name = "settId") Long id) throws Exception{
        List<Expense> expenses = expenseService.findAll();

        List<ExpenseDto> expenseDtos = expenses.stream()
                .filter(e -> e.getSettlement().getId().equals(id))
                .map(e -> {
                    return new ExpenseDto(e.getId(), e.getSettlement().getId(), e.getUser().getId(), e.getAmount(), e.getName());
                })
                .collect(Collectors.toList());

        return new Result(expenseDtos.size(), expenseDtos);
    }

}
