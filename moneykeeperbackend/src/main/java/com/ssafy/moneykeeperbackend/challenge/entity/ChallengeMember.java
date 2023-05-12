package com.ssafy.moneykeeperbackend.challenge.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeMember {
    @Id
    @GeneratedValue
    @Column(name="challenge_member_id")
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;


    //챌린지 성공여부
    @Column(nullable = false)
    private boolean isSuccess;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String logs ="";



}
