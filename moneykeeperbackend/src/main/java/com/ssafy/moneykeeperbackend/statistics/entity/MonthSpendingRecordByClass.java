package com.ssafy.moneykeeperbackend.statistics.entity;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
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
public class MonthSpendingRecordByClass {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private MajorSpendingClassification majorSpendingClass;
    private LocalDate ymonth;
    private int amount;
    @ManyToOne
    private Member member;
    @Builder
    public MonthSpendingRecordByClass(Member member, LocalDate ymonth, MajorSpendingClassification majorSpendingClass, int amount) {
        this.majorSpendingClass = majorSpendingClass;
        this.amount = amount;
        this.ymonth = ymonth;
        this.member = member;
    }
}
