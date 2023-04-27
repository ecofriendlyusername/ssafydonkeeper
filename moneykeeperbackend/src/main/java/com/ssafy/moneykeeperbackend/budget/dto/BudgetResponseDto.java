package com.ssafy.moneykeeperbackend.budget.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetResponseDto {
    private int budget;

    private int spending;
    int year;
    int month;

    @Builder
    public BudgetResponseDto(int budget, int spending, int year, int month) {
        this.budget = budget;
        this.spending = spending;
        this.year = year;
        this.month = month;
    }
}
