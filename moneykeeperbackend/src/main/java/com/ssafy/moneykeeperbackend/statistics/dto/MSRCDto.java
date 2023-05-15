package com.ssafy.moneykeeperbackend.statistics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MSRCDto {
    private String category;
    private int amount;

    private Long categoryId;
    @Builder
    public MSRCDto(String category, int amount, Long categoryId) {
        this.category = category;
        this.categoryId = categoryId;
        this.amount = amount;
    }
}
