package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthSpendingRecordDto {
    private int amount;

//    private int base;
//
//    private int below;
    private int groupAvg;
    @Builder
    public MonthSpendingRecordDto(int amount, int groupAvg) {
        this.amount = amount;
        this.groupAvg = groupAvg;
//        this.base = base;
//        this.below = below;
    }
}
