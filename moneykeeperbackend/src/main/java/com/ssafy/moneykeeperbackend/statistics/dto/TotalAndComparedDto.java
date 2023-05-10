package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalAndComparedDto {

    private String category;
    private int total;

    private int groupAvg;
    @Builder
    public TotalAndComparedDto(int total, int groupAvg) {
        this.total = total;
        this.groupAvg = groupAvg;
    }
}