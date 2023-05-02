package com.ssafy.moneykeeperbackend.statistics.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthIncomeRecord {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate ymonth;
    private int amount;
    @ManyToOne
    private Member member;

    @Builder
    public MonthIncomeRecord(Member member, LocalDate month, int amount) {
        this.amount = amount;
        this.ymonth = month;
        this.member = member;
    }
}
