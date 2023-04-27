package com.ssafy.moneykeeperbackend.budget.controller;

import com.ssafy.moneykeeperbackend.budget.dto.BudgetResponseDto;
import com.ssafy.moneykeeperbackend.budget.service.BudgetService;
import com.ssafy.moneykeeperbackend.record.dto.SpendingRequestDto;
import com.ssafy.moneykeeperbackend.record.dto.SpendingResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService budgetService;
    @GetMapping("")
    @Operation(summary = "이번달 예산과 이번달에 총 쓴 금액", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> getBudget(@RequestParam("id") String id) { // 나중에 바꿀 것
        BudgetResponseDto budget = budgetService.getBudget(Long.parseLong(id));

        return new ResponseEntity<BudgetResponseDto>(budget, HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(summary = "예산 결정", description = "이번달 예산을 결정한다"
            , responses = {
    })
    public ResponseEntity<?> setBudgetForThisMonth(@RequestParam("id") Long id, @RequestParam int budget) { // 나중에 바꿀 것
        budgetService.setBudget(id,budget);
        return ResponseEntity.ok().build();
    }
}
