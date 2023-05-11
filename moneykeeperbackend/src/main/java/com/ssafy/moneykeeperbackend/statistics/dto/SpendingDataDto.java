package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingDataDto {
    private String category;
    private int amount;
    @Builder
    public SpendingDataDto(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }
}
