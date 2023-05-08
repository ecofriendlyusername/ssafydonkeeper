package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthIncomeRecordDto {
    private int amount;
    @Builder
    public MonthIncomeRecordDto(int amount) {
        this.amount = amount;
    }
}
