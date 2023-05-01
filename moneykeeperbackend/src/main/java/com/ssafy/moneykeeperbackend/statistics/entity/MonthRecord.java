package com.ssafy.moneykeeperbackend.statistics.entity;
import lombok.*;

import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthRecord {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private SpendingClassification spendingClassification;
    private LocalDate ymonth;
    private int amount;
    @ManyToOne
    private Member member;
    @Builder
    public MonthRecord(Member member, LocalDate month, SpendingClassification spendingClassification, int amount) {
        this.spendingClassification = spendingClassification;
        this.amount = amount;
        this.ymonth = month;
        this.member = member;
    }
}
