package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompareWithRecentXDto {
    private String category;
    private int thisMonth;
    private int recentXAvg;
    @Builder
    public CompareWithRecentXDto(int thisMonth, int recentXAvg, String category) {
        this.thisMonth = thisMonth;
        this.recentXAvg = recentXAvg;
        this.category = category;
    }
}
