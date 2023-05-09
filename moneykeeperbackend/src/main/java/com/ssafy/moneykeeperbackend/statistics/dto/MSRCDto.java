package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MSRCDto {
    private String category;
    private int amount;
    @Builder
    public MSRCDto(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }
}
