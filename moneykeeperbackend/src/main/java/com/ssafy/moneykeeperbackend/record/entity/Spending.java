package com.ssafy.moneykeeperbackend.record.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Spending {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NonNull
    private SpendingClassification spendingClassification;
    @NonNull
    private int amount;
    @NonNull
    private LocalDate date;
    private String detail;
    private String memo;
    @Builder
    public Spending(SpendingClassification spendingClassification, int amount, LocalDate date, String detail, String memo, Member member) {
        this.spendingClassification = spendingClassification;
        this.amount = amount;
        this.detail = detail;
        this.memo = memo;
        this.date = date;
        this.member = member;
    }
    @ManyToOne
    private Member member;
}
