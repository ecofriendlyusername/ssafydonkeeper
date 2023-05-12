package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompareWithUserDto {
    private int total;
    private int groupAvg;

    private int base;

    private int below;

    List<SpendingDataDto> user;

    List<SpendingDataDto> group;

    @Builder
    public CompareWithUserDto(int base, int below, int total, int groupAvg, List<SpendingDataDto> user, List<SpendingDataDto> group) {
        this.base = base;
        this.below = below;
        this.total = total;
        this.groupAvg = groupAvg;
        this.user = user;
        this.group = group;
    }
}
