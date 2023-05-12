package com.ssafy.moneykeeperbackend.challenge.dto.resopnse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
public class ChallengeDetailResponseDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private int participantCount;
    private boolean Participant;
}
