package com.ssafy.moneykeeperbackend.challenge.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/*
    * entity
    * challenge
    *
    * @date 2023.05.04
    * @author 양윤정
    * */


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {

    @Id
    @GeneratedValue
    @Column(name = "challenge_id")
    private long id;

    @Column(nullable = false)
    private String name;
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;


    @Column(nullable = false)
    private boolean inProgress = false;

    @Column(nullable = false)
    private boolean isFinished = false;

}
