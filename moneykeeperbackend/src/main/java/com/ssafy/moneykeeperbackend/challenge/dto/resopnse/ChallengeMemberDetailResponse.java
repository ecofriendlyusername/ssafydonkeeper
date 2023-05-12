package com.ssafy.moneykeeperbackend.challenge.dto.resopnse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeMemberDetailResponse {

    private long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int remainingDuration;

    private String content;
    private String logs;


}
