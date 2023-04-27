package com.ssafy.moneykeeperbackend.record.dto;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingResponseDto {

    private Long id;
    private String classification;
    private int year;
    private int month;
    private int day;
    private int amount;
    private String detail;
    private String memo;
    private Long memberId;

    @Builder
    public SpendingResponseDto(Long id, String classification, int amount, int year, int month, int day, String detail, String memo, Long memberId) {
        this.id = id;
        this.classification = classification;
        this.amount = amount;
        this.detail = detail;
        this.memo = memo;
        this.year = year;
        this.month = month;
        this.day = day;
        this.memberId = memberId;
    }
}
