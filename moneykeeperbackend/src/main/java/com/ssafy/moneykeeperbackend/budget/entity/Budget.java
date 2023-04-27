package com.ssafy.moneykeeperbackend.budget.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private int budget;
    @NonNull
    private int spending;
    @NonNull
    LocalDate date;
    @Builder
    public Budget(int budget, int spending, LocalDate date, Member member) {
        this.budget = budget;
        this.spending = spending;
        this.date = date;
        this.member = member;
    }
    @OneToOne
    private Member member;
}
