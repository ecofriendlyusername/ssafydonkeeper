package com.ssafy.moneykeeperbackend.challenge.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ChallengeRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
}
