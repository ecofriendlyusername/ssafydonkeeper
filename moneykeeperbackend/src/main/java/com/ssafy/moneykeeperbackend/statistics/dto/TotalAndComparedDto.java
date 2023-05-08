package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalAndComparedDto {
    private int total;

    private int base;

    private int below;

    private int groupAvg;
    @Builder
    public TotalAndComparedDto(int total, int groupAvg, int base, int below) {
        this.total = total;
        this.groupAvg = groupAvg;
        this.base = base;
        this.below = below;
    }
}