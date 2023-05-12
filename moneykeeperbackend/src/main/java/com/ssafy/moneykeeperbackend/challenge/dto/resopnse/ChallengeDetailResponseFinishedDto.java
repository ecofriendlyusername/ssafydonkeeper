package com.ssafy.moneykeeperbackend.challenge.dto.resopnse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ChallengeDetailResponseFinishedDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String logs;
    private boolean isSuccess;


}
