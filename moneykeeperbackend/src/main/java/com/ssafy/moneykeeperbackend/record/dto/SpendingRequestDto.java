package com.ssafy.moneykeeperbackend.record.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingRequestDto {
    private Long classificationId;
    private int year;
    private int month;
    private int day;
    private int amount;
    private Long assetId;
    private String detail;
    private String memo;
}
